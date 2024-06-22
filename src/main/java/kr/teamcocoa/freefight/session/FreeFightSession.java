package kr.teamcocoa.freefight.session;

import ch.dkrieger.coinsystem.core.CoinSystem;
import ch.dkrieger.coinsystem.core.player.CoinPlayer;
import kr.teamcocoa.core.bukkit.utils.PacketUtils;
import kr.teamcocoa.core.network.webhook.DiscordWebhook;
import kr.teamcocoa.core.utils.StringUtils;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.mysql.SessionDatabase;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.replay.SessionReplay;
import kr.teamcocoa.freefight.session.webhook.WebhookSender;
import kr.teamcocoa.freefight.task.CountDownTask;
import kr.teamcocoa.freefight.task.MatchTask;
import kr.teamcocoa.freefight.translation.messages.KillLogMessage;
import kr.teamcocoa.freefight.translation.messages.MatchIdMessage;
import kr.teamcocoa.freefight.translation.messages.MatchInfoButtonMessage;
import kr.teamcocoa.freefight.translation.titles.*;
import kr.teamcocoa.freefight.utils.Serializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.nucha.swkilleffect.SWKillEffect;
import me.nucha.swkilleffect.effects.KillEffects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Getter
@EqualsAndHashCode
public class FreeFightSession {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20));

    private int id;

    private FreeFightPlayer freeFightPlayer1;
    private FreeFightPlayer freeFightPlayer2;
    private Kits kits;
    private boolean running;

    @Setter
    private boolean damageAble;

    private SessionReplay sessionReplay;

    @Setter
    private MatchTask matchTask;

    @Setter
    private boolean antiCheatDetect;

    private HashMap<String, Integer> detectedAntiCheatFlags;

    protected FreeFightSession(FreeFightPlayer freeFightPlayer1, FreeFightPlayer freeFightPlayer2, Kits kit) {
        this.freeFightPlayer1 = freeFightPlayer1;
        this.freeFightPlayer2 = freeFightPlayer2;
        this.kits = kit;
        this.running = false;
        this.damageAble = false;
        this.antiCheatDetect = false;
        this.detectedAntiCheatFlags = new HashMap<>();
    }

    public boolean initId() {
        if(id != 0) {
            throw new IllegalStateException("id is already initialized!");
        }

        int receivedId = SessionDatabase.registerId(this);

        if(receivedId == -1) {
            return false;
        }

        this.id = receivedId;
        return true;
    }

    public void initReplay() {
        if(id == 0) {
            throw new IllegalStateException("id isn't initialized!");
        }
        this.sessionReplay = new SessionReplay(this);
    }

    public void start() {
        if(id == 0 || running) {
            return;
        }
        Player player1 = freeFightPlayer1.getPlayer();
        Player player2 = freeFightPlayer2.getPlayer();
        for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
            Player onlinePlayer = freeFightPlayer.getPlayer();
            if(onlinePlayer == player1 || onlinePlayer == player2) {
                continue;
            }
            if(freeFightPlayer.getState() == GameState.INGAME ||
                    (freeFightPlayer.getState() == GameState.LOBBY &&
                            !freeFightPlayer.getSettings().isDisplaySessionPlayers())) {
                onlinePlayer.hidePlayer(FreeFight.getInstance(), player1);
                onlinePlayer.hidePlayer(FreeFight.getInstance(), player2);
            }
            player1.hidePlayer(FreeFight.getInstance(), onlinePlayer);
            player2.hidePlayer(FreeFight.getInstance(), onlinePlayer);
        }

        freeFightPlayer1.resetPlayer();
        freeFightPlayer2.resetPlayer();

        freeFightPlayer1.setState(GameState.INGAME);
        freeFightPlayer2.setState(GameState.INGAME);

        freeFightPlayer1.setInventory(GameState.INGAME);
        freeFightPlayer2.setInventory(GameState.INGAME);

        sessionReplay.startReplay();

        new CountDownTask(this).runTaskTimer(FreeFight.getInstance(), 0L, 20L);

        running = true;

        executor.execute(() -> {
            try {
                DiscordWebhook webhook = WebhookSender.getNewWebHook();

                webhook.addEmbed(WebhookSender.getSessionStartWebhookEmbed(this));

                webhook.execute();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void stop(FreeFightPlayer loser) {
        if(!running) {
            return;
        }

        Player player1 = freeFightPlayer1.getPlayer();
        Player player2 = freeFightPlayer2.getPlayer();
        for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
            if(freeFightPlayer.getState() == GameState.LOBBY) {
                freeFightPlayer.getPlayer().showPlayer(FreeFight.getInstance(), player1);
                freeFightPlayer.getPlayer().showPlayer(FreeFight.getInstance(), player2);
                player1.showPlayer(FreeFight.getInstance(), freeFightPlayer.getPlayer());
                player2.showPlayer(FreeFight.getInstance(), freeFightPlayer.getPlayer());
            }
            if(freeFightPlayer.getState() == GameState.INGAME) {
                if(freeFightPlayer1.getSettings().isDisplaySessionPlayers()) {
                    player1.showPlayer(FreeFight.getInstance(), freeFightPlayer.getPlayer());
                }
                if(freeFightPlayer2.getSettings().isDisplaySessionPlayers()) {
                    player2.showPlayer(FreeFight.getInstance(), freeFightPlayer.getPlayer());
                }
            }
        }

        damageAble = false;

        ItemStack[] player1Inventory = player1.getInventory().getContents().clone();
        ItemStack[] player2Inventory = player2.getInventory().getContents().clone();

        double player1DamageIn = freeFightPlayer1.getDamageIn();
        double player1DamageOut = freeFightPlayer1.getDamageOut();
        double player2DamageIn = freeFightPlayer2.getDamageIn();
        double player2DamageOut = freeFightPlayer2.getDamageOut();

        double player1Health = player1.getHealth();
        double player2Health = player2.getHealth();

        float player1Saturation = player1.getSaturation();
        float player2Saturation = player2.getSaturation();

        int player1Hunger = player1.getFoodLevel();
        int player2Hunger = player2.getFoodLevel();

        sessionReplay.stopReplay();

        SessionManager.removeSession(this);

        freeFightPlayer1.setState(GameState.LOBBY);
        freeFightPlayer2.setState(GameState.LOBBY);

        freeFightPlayer1.setInventory(GameState.LOBBY);
        freeFightPlayer2.setInventory(GameState.LOBBY);

        // 무승부가 아닐때
        if(loser != null) {
            FreeFightPlayer winner = loser == freeFightPlayer1 ? freeFightPlayer2 : freeFightPlayer1;

            if (!FreeFight.isForceTPMode()) {
                loser.death();
                winner.kill();
            }

            FinishGameTitle finishGameTitle = new FinishGameTitle(winner.getPlayer().getName(), winner.getPlayer().getHealth());

            PacketUtils.sendTitle(
                    winner.getPlayer(),
                    VictoryTitle.getInstance().getMessage(winner.getPlayer()),
                    finishGameTitle.getMessage(winner.getPlayer()),
                    10, 80, 10);

            PacketUtils.sendTitle(
                    loser.getPlayer(),
                    DefeatTitle.getInstance().getMessage(loser.getPlayer()),
                    finishGameTitle.getMessage(loser.getPlayer()),
                    10, 80, 10);

            KillLogMessage killLogMessage = new KillLogMessage(winner.getPlayer().getName(), loser.getPlayer().getName(), winner.getPlayer().getHealth(), kits);
            for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
                String message = killLogMessage.getMessage(freeFightPlayer.getPlayer());
                Component killLogMessageComponent = Component.text(message)
                        .clickEvent(ClickEvent.runCommand(("/checkmatch " + id).replace(",", "")))
                        .hoverEvent(HoverEvent.showText(Component.text(("/checkmatch " + id).replace(",", ""))));
                freeFightPlayer.getPlayer().sendMessage(killLogMessageComponent);
            }

            Player winnerPlayer = winner.getPlayer();
            Player loserPlayer = loser.getPlayer();

            if(winnerPlayer.hasPermission("teamcocoa.killeffect")) {
                KillEffects killEffects = SWKillEffect.killEffectManager.getKillEffect(winnerPlayer);
                if(killEffects != KillEffects.NONE) {
                    SWKillEffect.killEffectManager.getKillEffectById(killEffects).play(loserPlayer, Arrays.asList(winnerPlayer, loserPlayer));
                }
            }

            CoinPlayer coinPlayer = CoinSystem.getInstance().getPlayerManager().getPlayer(winnerPlayer.getUniqueId());
            coinPlayer.addCoins(100);
            winnerPlayer.sendMessage(StringUtils.color("&a[&dTeamCocoa&a] &6+100 coins!"));

        }
        else {
            PacketUtils.sendTitle(player1,
                    DrawTitle.getInstance().getMessage(player1),
                    DrawGameTitle.getInstance().getMessage(player1),
                    10, 80, 10);
            PacketUtils.sendTitle(player2,
                    DrawTitle.getInstance().getMessage(player2),
                    DrawGameTitle.getInstance().getMessage(player2),
                    10, 80, 10);
        }

        freeFightPlayer1.resetPlayer();
        freeFightPlayer2.resetPlayer();

        freeFightPlayer1.getSpectators().forEach(FreeFightPlayer::stopSpectate);
        freeFightPlayer2.getSpectators().forEach(FreeFightPlayer::stopSpectate);

        player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5F, 100F);
        player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 5F, 100F);

        player1.setArrowsInBody(0);
        player2.setArrowsInBody(0);

        running = false;

        executor.execute(() -> {

            MatchIdMessage matchIdMessage = new MatchIdMessage(id);

            TextComponent player1InfoButton = Component.text(MatchInfoButtonMessage.getInstance().getMessage(player1))
                    .clickEvent(ClickEvent.runCommand(("/checkmatch " + id).replace(",", "")))
                    .hoverEvent(HoverEvent.showText(Component.text(("/checkmatch " + id).replace(",", ""))));

            TextComponent player2InfoButton = Component.text(MatchInfoButtonMessage.getInstance().getMessage(player2))
                    .clickEvent(ClickEvent.runCommand(("/checkmatch " + id).replace(",", "")))
                    .hoverEvent(HoverEvent.showText(Component.text(("/checkmatch " + id).replace(",", ""))));

            Component player1MatchInfoMessage = Component.text(StringUtils.color("&7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n"))
                    .append(Component.text("    " + matchIdMessage.getMessage(player1)))
                    .append(Component.text("\n    "))
                    .append(player1InfoButton)
                    .append(Component.text(StringUtils.color("\n&7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")));

            Component player2MatchInfoMessage = Component.text(StringUtils.color("&7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n"))
                    .append(Component.text("    " + matchIdMessage.getMessage(player2)))
                    .append(Component.text("\n    "))
                    .append(player2InfoButton)
                    .append(Component.text(StringUtils.color("\n&7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")));

            player1.sendMessage(player1MatchInfoMessage);
            player2.sendMessage(player2MatchInfoMessage);

            byte[] serialized1Inv = Serializer.itemStacksToBytes(player1Inventory);
            byte[] serialized2Inv = Serializer.itemStacksToBytes(player2Inventory);

            DiscordWebhook webhook = WebhookSender.getNewWebHook();

            // 무승부 일때
            if(loser == null) {
                SessionDatabase.finishGame(id, null, null,
                        serialized1Inv, serialized2Inv, player1DamageIn, player1DamageOut, player2DamageIn, player2DamageOut,
                        player1Health, player2Health, player1Saturation, player2Saturation, player1Hunger, player2Hunger);

                webhook.addEmbed(WebhookSender.getSessionEndWebhookEmbed(this, null, null));
            }
            else {
                FreeFightPlayer winner = loser == freeFightPlayer1 ? freeFightPlayer2 : freeFightPlayer1;
                SessionDatabase.finishGame(id,
                        winner.getPlayer().getUniqueId(),
                        loser.getPlayer().getUniqueId(),
                        serialized1Inv, serialized2Inv, player1DamageIn, player1DamageOut, player2DamageIn, player2DamageOut,
                        player1Health, player2Health, player1Saturation, player2Saturation, player1Hunger, player2Hunger);

                webhook.addEmbed(WebhookSender.getSessionEndWebhookEmbed(this, winner, loser));
            }

            if(antiCheatDetect) {
                StringBuilder sb = new StringBuilder("<@&736123325172154398>\nAnticheat detected.\n\nFlags:\n");

                detectedAntiCheatFlags.forEach((flag, count) -> {
                    sb.append(flag + " " + count + (count <= 1 ? "time" : "times") + "\n");
                });

                webhook.setContent(sb.toString());
            }

            try {
                webhook.execute();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        Bukkit.getScheduler().runTaskLater(FreeFight.getInstance(), () -> {
            freeFightPlayer1.setChallengeAble(true);
            freeFightPlayer2.setChallengeAble(true);
        }, 30L);

    }

}
