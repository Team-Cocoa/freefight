package kr.teamcocoa.freefight.player;

import kr.teamcocoa.freefight.items.lobby.ChallengeItem;
import kr.teamcocoa.freefight.items.lobby.KitSelectItem;
import kr.teamcocoa.freefight.items.lobby.SpectateItem;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.scoreboard.ScoreboardManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import kr.teamcocoa.freefight.tab.TabManager;
import kr.teamcocoa.freefight.translation.messages.ChallengeMessage;
import kr.teamcocoa.freefight.translation.messages.ChallengedMessage;
import kr.teamcocoa.freefight.translation.messages.DifferentKitMessage;
import kr.teamcocoa.freefight.translation.messages.SessionErrorMessage;
import kr.teamcocoa.freefight.utils.StringUtils;
import kr.teamcocoa.freefight.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Getter
public class FreeFightPlayer {

    private Player player;

    @Setter
    private GameState state;

    private Kits currentKit;

    private Stats stats;

    @Setter
    private boolean challengeAble;

    @Setter
    private boolean pearlThrowable;

    // Thread safe 한 LinkedList 가 없어서 어거지라도 이거 써야지 :sadblob:
    private LinkedBlockingQueue<FreeFightPlayer> challengedPlayerList;

    protected FreeFightPlayer(Player player) {
        this.player = player;
        this.state = GameState.LOBBY;
        this.currentKit = Kits.ONLYSWORD;
        this.challengedPlayerList = new LinkedBlockingQueue<>();
        this.challengeAble = true;
        this.pearlThrowable = true;

        this.stats = new Stats(player.getUniqueId());
    }

    public void join() {
        // 동기 실행할 몇몇 코드들
        moveToSpawn();
        setInventory(GameState.LOBBY);
        Executors.newSingleThreadExecutor().execute(() -> {
            // 비동기 실행할 몇몇 코드들
            stats.loadStats();
        });

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> ScoreboardManager.sendScoreboard(player), 0, 1, TimeUnit.SECONDS);

        for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
            if(freeFightPlayer.getState() == GameState.INGAME) {
                player.hidePlayer(FreeFight.getInstance(), freeFightPlayer.getPlayer());
                freeFightPlayer.getPlayer().hidePlayer(FreeFight.getInstance(), player);
            }
        }

    }

    public void quit() {
        // 동기 실행할 몇몇 코드들

        Executors.newSingleThreadExecutor().execute(() -> {
            stats.saveStats();
        });
    }

    public void setInventory(GameState state) {
        switch (state) {
            case LOBBY -> {
                Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
                   player.getInventory().clear();
                   player.getInventory().setItem(0, ChallengeItem.getInstance().toItemStack(player));
                   player.getInventory().setItem(4, SpectateItem.getInstance().toItemStack(player));
                   player.getInventory().setItem(8, KitSelectItem.getInstance().toItemStack(player));
                });
            }
            case INGAME -> {
                Kits.makePlayerKit(player, currentKit);
            }
        }
    }

    public void changeKit(Kits kit) {
        if(state != GameState.LOBBY) {
            return;
        }
        for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
            freeFightPlayer.getChallengedPlayerList().remove(this);
        }
        challengedPlayerList.clear();
        currentKit = kit;
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> TabManager.updateNameTags(player));
    }

    public void moveToSpawn() {
        Location location = new Location(Bukkit.getWorld("TestFreeFight"), 0, 101, 0);
        player.teleport(location);
    }

    public void death() {
        stats.addDeaths();
    }

    public void kill() {
        stats.addKills();
    }

    public void challenge(FreeFightPlayer enemyFightPlayer) {
        if(!challengeAble) {
            return;
        }

        // 내 state 가 LOBBY 인가?
        // 만약 INGAME 이거나 SPECTATE 면 챌린지를 할 수 없음
        if(state != GameState.LOBBY) {
            return;
        }

        // 상대도 똑같이!
        if(enemyFightPlayer.getState() != GameState.LOBBY) {
            return;
        }

        // 이전에 그 상대방에게 듀얼을 걸었는지?
        if(challengedPlayerList.contains(enemyFightPlayer)) {
            return;
        }

        // 상대방의 킷이랑 내 킷이랑 같은지?
        if(currentKit != enemyFightPlayer.getCurrentKit()) {
            DifferentKitMessage differentKitMessage = new DifferentKitMessage(enemyFightPlayer.getCurrentKit());
            player.sendMessage(differentKitMessage.getMessage(player));
            return;
        }

        // 만약에 상대는 이미 나한테 듀얼을 건 적이 있는지?
        if(enemyFightPlayer.getChallengedPlayerList().contains(this)) {
            for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
                freeFightPlayer.getChallengedPlayerList().remove(this);
                freeFightPlayer.getChallengedPlayerList().remove(enemyFightPlayer);
            }
            boolean created = SessionManager.addSession(this, enemyFightPlayer, this.currentKit);
            if(created) {
                FreeFightSession session = SessionManager.getSession(this);
                session.start();
            }
            else {
                player.sendMessage(SessionErrorMessage.getInstance().getMessage(player));
                enemyFightPlayer.getPlayer().sendMessage(SessionErrorMessage.getInstance().getMessage(enemyFightPlayer.getPlayer()));
            }
            return;
        }

        challengedPlayerList.add(enemyFightPlayer);
        ChallengeMessage challengeMessage = new ChallengeMessage(enemyFightPlayer.getPlayer());
        ChallengedMessage challengedMessage = new ChallengedMessage(player);
        player.sendMessage(challengeMessage.getMessage(player));
        enemyFightPlayer.getPlayer().sendMessage(challengedMessage.getMessage(enemyFightPlayer.getPlayer()));

    }

    public void resetPlayer() {
        Utils.catchAsynchronous();
        player.setLevel(0);
        for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(activePotionEffect.getType());
        }
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setSaturation(12);
    }

}
