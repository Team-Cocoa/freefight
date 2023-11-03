package kr.teamcocoa.freefight.player;

import kr.teamcocoa.freefight.items.lobby.ChallengeItem;
import kr.teamcocoa.freefight.items.lobby.KillEffectItem;
import kr.teamcocoa.freefight.items.lobby.KitSelectItem;
import kr.teamcocoa.freefight.items.lobby.SpectateItem;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.scoreboard.ScoreboardManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import kr.teamcocoa.freefight.tab.TabManager;
import kr.teamcocoa.freefight.translation.messages.*;
import kr.teamcocoa.freefight.utils.Utils;
import lombok.Getter;
import lombok.Setter;
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

    private GameState state;

    private Kits currentKit;

    private Stats stats;

    @Setter
    private boolean challengeAble;

    @Setter
    private boolean pearlThrowable;

    // Thread safe 한 LinkedList 가 없어서 어거지라도 이거 써야지 :sadblob:
    private LinkedBlockingQueue<FreeFightPlayer> challengedPlayerList;

    @Setter
    private double damageOut;

    @Setter
    private double damageIn;

    @Setter
    private long lastMovingTime;

    private long lastStateChangeTime;

    protected FreeFightPlayer(Player player) {
        this.player = player;
        this.state = GameState.LOBBY;
        this.currentKit = Kits.ONLYSWORD;
        this.challengedPlayerList = new LinkedBlockingQueue<>();
        this.challengeAble = true;
        this.pearlThrowable = true;

        this.stats = new Stats(player.getUniqueId());

        this.damageOut = 0.0;
        this.damageIn = 0.0;

        this.lastMovingTime = System.currentTimeMillis();
        this.lastStateChangeTime = System.currentTimeMillis();
    }

    public void setState(GameState state) {
        this.state = state;
        this.lastStateChangeTime = System.currentTimeMillis();
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
            case LOBBY, SPECTATE -> {
                Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
                   player.getInventory().clear();
                   player.getInventory().setItem(0, ChallengeItem.getInstance().toItemStack(player));
                   player.getInventory().setItem(8, KitSelectItem.getInstance().toItemStack(player));
                   if(player.hasPermission("teamcocoa.killeffect")) {
                       player.getInventory().setItem(3, SpectateItem.getInstance().toItemStack(player));
                       player.getInventory().setItem(5, KillEffectItem.getInstance().toItemStack(player));
                   }
                   else {
                       player.getInventory().setItem(4, SpectateItem.getInstance().toItemStack(player));
                   }
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

        if(!enemyFightPlayer.isChallengeAble()) {
            return;
        }

        // 내 state 가 LOBBY 인가?
        // 만약 INGAME 이거나 SPECTATE 면 챌린지를 할 수 없음
        if(state != GameState.LOBBY) {
            // state 가 SPECTATE 면 듀얼 불가 메시지 보내기
            if(state == GameState.SPECTATE) {
                player.sendMessage(CantDuelMessage.getInstance().getMessage(player));
            }
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

            // 나한테 챌린지 걸었던 사람들 전부 나 제거
            for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
                freeFightPlayer.getChallengedPlayerList().remove(this);
                freeFightPlayer.getChallengedPlayerList().remove(enemyFightPlayer);
            }

            // 서로 챌린지 걸었던 사람들 전부 제거
            getChallengedPlayerList().clear();
            enemyFightPlayer.getChallengedPlayerList().clear();

            /*
             * 서로의 인벤토리 창을 초기화 함으로써
             * 다른 스레드에서 세션 추가 작업을 하는 동안
             * 다른 듀얼을 걸지 못하도록 처리
             */

            challengeAble = false;
            enemyFightPlayer.setChallengeAble(false);

            Executors.newSingleThreadExecutor().execute(() -> {
                boolean created = SessionManager.addSession(this, enemyFightPlayer, this.currentKit);
                if(created) {
                    FreeFightSession session = SessionManager.getSession(this);
                    boolean initSuccess = session.initId();
                    if(initSuccess) {
                        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
                            session.initReplay();
                            session.start();
                        });
                    }
                    else {
                        player.sendMessage(SessionErrorMessage.getInstance().getMessage(player));
                        enemyFightPlayer.getPlayer().sendMessage(SessionErrorMessage.getInstance().getMessage(enemyFightPlayer.getPlayer()));
                    }
                }
                else {
                    player.sendMessage(SessionErrorMessage.getInstance().getMessage(player));
                    enemyFightPlayer.getPlayer().sendMessage(SessionErrorMessage.getInstance().getMessage(enemyFightPlayer.getPlayer()));
                }
            });
        }
        else {
            challengedPlayerList.add(enemyFightPlayer);
            ChallengeMessage challengeMessage = new ChallengeMessage(enemyFightPlayer.getPlayer());
            ChallengedMessage challengedMessage = new ChallengedMessage(player);
            player.sendMessage(challengeMessage.getMessage(player));
            enemyFightPlayer.getPlayer().sendMessage(challengedMessage.getMessage(enemyFightPlayer.getPlayer()));
        }
    }

    public void resetPlayer() {
        Utils.catchAsynchronous();
        player.setLevel(0);
        player.setExp(0);
        player.setTotalExperience(0);
        for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(activePotionEffect.getType());
        }
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setSaturation(12);
    }

    public void addDamageIn(double value) {
        this.damageIn += value;
    }

    public void addDamageOut(double value) {
        this.damageOut += value;
    }

}
