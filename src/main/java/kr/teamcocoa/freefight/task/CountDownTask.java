package kr.teamcocoa.freefight.task;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.translation.titles.EnemyTitle;
import kr.teamcocoa.freefight.utils.PlayerUtils;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class CountDownTask extends BukkitRunnable {

    private FreeFightSession freeFightSession;

    private FreeFightPlayer freeFightPlayer1;
    private FreeFightPlayer freeFightPlayer2;

    private Player player1;
    private Player player2;

    private int count = 3;

    public CountDownTask(FreeFightSession freeFightSession) {
        this.freeFightSession = freeFightSession;

        this.freeFightPlayer1 = freeFightSession.getFreeFightPlayer1();
        this.freeFightPlayer2 = freeFightSession.getFreeFightPlayer2();

        this.player1 = this.freeFightPlayer1.getPlayer();
        this.player2 = this.freeFightPlayer2.getPlayer();

    }

    @Override
    public void run() {

        if(!freeFightSession.isRunning()) {
            cancel();
            return;
        }

        switch (count) {
            case 3 -> {
                PlayerUtils.sendTitle(player1, "&e3", "", 5, 10, 5);
                PlayerUtils.sendTitle(player2, "&e3", "", 5, 10, 5);
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
            }
            case 2 -> {
                PlayerUtils.sendTitle(player1, "&c2", "", 5, 10, 5);
                PlayerUtils.sendTitle(player2, "&c2", "", 5, 10, 5);
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
            }
            case 1 -> {
                PlayerUtils.sendTitle(player1, "&41", "", 5, 10, 5);
                PlayerUtils.sendTitle(player2, "&41", "", 5, 10, 5);
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
            }
            case 0 -> {

                EnemyTitle

                PlayerUtils.sendTitle(freeFightPlayer1.getPlayer(), "&6Game Start!", "&7Your enemy is &a" + freeFightPlayer2.getPlayer().getName(), 10, 40, 10);
                PlayerUtils.sendTitle(freeFightPlayer2.getPlayer(), "&6Game Start!", "&7Your enemy is &a" + freeFightPlayer1.getPlayer().getName(), 10, 40, 10);

                freeFightPlayer1.getPlayer().playSound(freeFightPlayer1.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0F, 100F);
                freeFightPlayer2.getPlayer().playSound(freeFightPlayer2.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0F, 100F);

                freeFightSession.setDamageAble(true);

                cancel();
            }
        }
        count--;
    }
}
