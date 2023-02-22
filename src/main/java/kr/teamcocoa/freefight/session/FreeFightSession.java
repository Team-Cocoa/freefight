package kr.teamcocoa.freefight.session;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.mysql.SessionDatabase;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.replay.LogType;
import kr.teamcocoa.freefight.replay.SessionReplay;
import kr.teamcocoa.freefight.task.CountDownTask;
import kr.teamcocoa.freefight.translation.messages.KillLogMessage;
import kr.teamcocoa.freefight.translation.titles.DefeatTitle;
import kr.teamcocoa.freefight.translation.titles.FinishGameTitle;
import kr.teamcocoa.freefight.translation.titles.VictoryTitle;
import kr.teamcocoa.freefight.utils.PlayerUtils;
import kr.teamcocoa.freefight.utils.Serializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.Executors;

@Getter
@EqualsAndHashCode
public class FreeFightSession {

    private int id;

    private FreeFightPlayer freeFightPlayer1;
    private FreeFightPlayer freeFightPlayer2;
    private Kits kits;
    private boolean running;

    @Setter
    private boolean damageAble;

    private SessionReplay sessionReplay;

    protected FreeFightSession(FreeFightPlayer freeFightPlayer1, FreeFightPlayer freeFightPlayer2, Kits kit) {
        this.freeFightPlayer1 = freeFightPlayer1;
        this.freeFightPlayer2 = freeFightPlayer2;
        this.kits = kit;
        this.running = false;
        this.damageAble = false;
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
            if(freeFightPlayer.getState() != GameState.SPECTATE) {
                onlinePlayer.hidePlayer(FreeFight.getInstance(), player1);
                onlinePlayer.hidePlayer(FreeFight.getInstance(), player2);
            }
            player1.hidePlayer(FreeFight.getInstance(), onlinePlayer);
            player2.hidePlayer(FreeFight.getInstance(), onlinePlayer);
        }

        freeFightPlayer1.resetPlayer();
        freeFightPlayer2.resetPlayer();

        freeFightPlayer1.setChallengeAble(false);
        freeFightPlayer2.setChallengeAble(false);

        freeFightPlayer1.setState(GameState.INGAME);
        freeFightPlayer2.setState(GameState.INGAME);

        freeFightPlayer1.setInventory(GameState.INGAME);
        freeFightPlayer2.setInventory(GameState.INGAME);

        freeFightPlayer1.getChallengedPlayerList().clear();
        freeFightPlayer2.getChallengedPlayerList().clear();

        sessionReplay.startReplay();

        new CountDownTask(this).runTaskTimer(FreeFight.getInstance(), 0L, 20L);

        running = true;
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
        }

        FreeFightPlayer winner = loser == freeFightPlayer1 ? freeFightPlayer2 : freeFightPlayer1;

        if(!FreeFight.isForceTPMode()) {
            loser.death();
            winner.kill();
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

        sessionReplay.addMessage(LogType.ARENA, winner.getPlayer().getName() + "has won the session");
        sessionReplay.stopReplay();

        SessionManager.removeSession(this);

        freeFightPlayer1.setState(GameState.LOBBY);
        freeFightPlayer2.setState(GameState.LOBBY);

        freeFightPlayer1.setInventory(GameState.LOBBY);
        freeFightPlayer2.setInventory(GameState.LOBBY);

        FinishGameTitle finishGameTitle = new FinishGameTitle(winner.getPlayer().getName(), winner.getPlayer().getHealth());

        PlayerUtils.sendTitle(
                winner.getPlayer(),
                VictoryTitle.getInstance().getMessage(winner.getPlayer()),
                finishGameTitle.getMessage(winner.getPlayer()),
                10, 80, 10);

        PlayerUtils.sendTitle(
                loser.getPlayer(),
                DefeatTitle.getInstance().getMessage(loser.getPlayer()),
                finishGameTitle.getMessage(loser.getPlayer()),
                10, 80, 10);

        KillLogMessage killLogMessage = new KillLogMessage(winner.getPlayer().getName(), loser.getPlayer().getName(), winner.getPlayer().getHealth(), kits);
        for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
            String message = killLogMessage.getMessage(freeFightPlayer.getPlayer());
            freeFightPlayer.getPlayer().sendMessage(Component.text(message));
        }

        freeFightPlayer1.resetPlayer();
        freeFightPlayer2.resetPlayer();

        winner.getPlayer().playSound(winner.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5F, 100F);
        loser.getPlayer().playSound(loser.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 5F, 100F);

        winner.getPlayer().setArrowsInBody(0);
        loser.getPlayer().setArrowsInBody(0);

        Bukkit.getScheduler().runTaskLater(FreeFight.getInstance(), () -> {
            freeFightPlayer1.setChallengeAble(true);
            freeFightPlayer2.setChallengeAble(true);
        }, 30L);

        running = false;

        Executors.newSingleThreadExecutor().execute(() -> {
            byte[] serialized1Inv = Serializer.itemStacksToString(player1Inventory);
            byte[] serialized2Inv = Serializer.itemStacksToString(player2Inventory);
            SessionDatabase.finishGame(id, winner.getPlayer().getUniqueId(), loser.getPlayer().getUniqueId(),
                    serialized1Inv, serialized2Inv, player1DamageIn, player1DamageOut, player2DamageIn, player2DamageOut,
                    player1Health, player2Health, player1Saturation, player2Saturation, player1Hunger, player2Hunger);
        });
    }

}
