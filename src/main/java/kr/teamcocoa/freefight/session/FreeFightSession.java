package kr.teamcocoa.freefight.session;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.task.CountDownTask;
import kr.teamcocoa.freefight.utils.PlayerUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.text.MessageFormat;

@Getter
@EqualsAndHashCode
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

        loser.death();
        winner.kill();

        damageAble = false;

        SessionManager.removeSession(this);

        freeFightPlayer1.setState(GameState.LOBBY);
        freeFightPlayer2.setState(GameState.LOBBY);

        freeFightPlayer1.setInventory(GameState.LOBBY);
        freeFightPlayer2.setInventory(GameState.LOBBY);

        PlayerUtils.sendTitle(
                winner.getPlayer(),
                "&aVICTORY",
                MessageFormat.format("&a{0} &7has won the fight &0(&7{1} &4❤&0)", winner.getPlayer().getName(), String.format("%.2f", winner.getPlayer().getHealth())),
                10, 80, 10);

        PlayerUtils.sendTitle(
                loser.getPlayer(),
                "&cDEFEAT",
                MessageFormat.format("&a{0} &7has won the fight &0(&7{1} &4❤&0)", winner.getPlayer().getName(), String.format("%.2f", winner.getPlayer().getHealth())),
                10, 80, 10);

        freeFightPlayer1.resetPlayer();
        freeFightPlayer2.resetPlayer();

        winner.getPlayer().playSound(winner.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5F, 100F);
        loser.getPlayer().playSound(loser.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 5F, 100F);

        winner.getPlayer().setArrowsInBody(0);
        loser.getPlayer().setArrowsInBody(0);

        Bukkit.getScheduler().runTaskLater(FreeFight.getInstance(), () -> {
            freeFightPlayer1.setChallengeAble(true);
            freeFightPlayer2.setChallengeAble(true);
        }, 60L);

        running = false;
    }

}
