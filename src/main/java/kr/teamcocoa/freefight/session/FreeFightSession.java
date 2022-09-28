package kr.teamcocoa.freefight.session;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.player.Kits;
import kr.teamcocoa.freefight.task.CountDownTask;
import kr.teamcocoa.freefight.utils.PlayerUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Getter
public class FreeFightSession {

    private FreeFightPlayer freeFightPlayer1;
    private FreeFightPlayer freeFightPlayer2;
    private Kits kits;
    private boolean running;

    @Setter
    private boolean damageAble;

    protected FreeFightSession(FreeFightPlayer freeFightPlayer1, FreeFightPlayer freeFightPlayer2, Kits kit) {
        this.freeFightPlayer1 = freeFightPlayer1;
        this.freeFightPlayer2 = freeFightPlayer2;
        this.kits = kit;
        this.running = false;
        this.damageAble = false;
    }

    public void start() {
        if(running) {
            return;
        }
        Player player1 = freeFightPlayer1.getPlayer();
        Player player2 = freeFightPlayer2.getPlayer();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(onlinePlayer == player1 || onlinePlayer == player2) {
                continue;
            }
            onlinePlayer.hidePlayer(FreeFight.getInstance(), player1);
            onlinePlayer.hidePlayer(FreeFight.getInstance(), player2);
            player1.hidePlayer(FreeFight.getInstance(), onlinePlayer);
            player2.hidePlayer(FreeFight.getInstance(), onlinePlayer);
        }

        freeFightPlayer1.setChallengeAble(false);
        freeFightPlayer2.setChallengeAble(false);

        freeFightPlayer1.setState(GameState.INGAME);
        freeFightPlayer2.setState(GameState.INGAME);

        freeFightPlayer1.setInventory(GameState.INGAME);
        freeFightPlayer2.setInventory(GameState.INGAME);

        new CountDownTask(this).runTaskTimer(FreeFight.getInstance(), 0L, 20L);

        running = true;
    }

    public void stop(FreeFightPlayer loser) {
        if(!running) {
            return;
        }

        Player player1 = freeFightPlayer1.getPlayer();
        Player player2 = freeFightPlayer2.getPlayer();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(FreeFight.getInstance(), player1);
            onlinePlayer.showPlayer(FreeFight.getInstance(), player2);
            player1.showPlayer(FreeFight.getInstance(), onlinePlayer);
            player2.showPlayer(FreeFight.getInstance(), onlinePlayer);
        }

        FreeFightPlayer winner = loser == freeFightPlayer1 ? freeFightPlayer2 : freeFightPlayer1;

        loser.death();
        winner.kill();

        damageAble = false;

        SessionManager.removeSession(this);

        freeFightPlayer1.setState(GameState.LOBBY);
        freeFightPlayer2.setState(GameState.LOBBY);

        freeFightPlayer1.setInventory(GameState.LOBBY);
        freeFightPlayer2.setInventory(GameState.LOBBY);

        freeFightPlayer1.getPlayer().setHealth(20);
        freeFightPlayer2.getPlayer().setHealth(20);

        PlayerUtils.sendTitle(winner.getPlayer(), "&aVICTORY", "&a" + winner.getPlayer().getName() + "&7 has won the fight", 10, 80, 10);
        PlayerUtils.sendTitle(loser.getPlayer(), "&cDEFEAT", "&a" + winner.getPlayer().getName() + "&7 has won the fight", 10, 80, 10);

//        Location loserLocation = loser.getPlayer().getLocation();

        winner.getPlayer().playSound(winner.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5F, 100F);
        loser.getPlayer().playSound(loser.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 5F, 100F);

        Bukkit.getScheduler().runTaskLater(FreeFight.getInstance(), () -> {
            freeFightPlayer1.setChallengeAble(true);
            freeFightPlayer2.setChallengeAble(true);
        }, 60L);

//        PlayerUtils.sendFakeLightning(player1, loserLocation.getX(), loserLocation.getY(), loserLocation.getZ());
//        PlayerUtils.sendFakeLightning(player2, loserLocation.getX(), loserLocation.getY(), loserLocation.getZ());

        running = false;
    }

}
