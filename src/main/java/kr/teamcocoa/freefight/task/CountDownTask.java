package kr.teamcocoa.freefight.task;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.replay.LogType;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.translation.titles.EnemyTitle;
import kr.teamcocoa.freefight.translation.titles.StartGameTitle;
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
                freeFightSession.getSessionReplay().addMessage(LogType.ARENA, "The session starts in " + count + "seconds.");
            }
            case 2 -> {
                PlayerUtils.sendTitle(player1, "&c2", "", 5, 10, 5);
                PlayerUtils.sendTitle(player2, "&c2", "", 5, 10, 5);
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                freeFightSession.getSessionReplay().addMessage(LogType.ARENA, "The session starts in " + count + "seconds.");
            }
            case 1 -> {
                PlayerUtils.sendTitle(player1, "&41", "", 5, 10, 5);
                PlayerUtils.sendTitle(player2, "&41", "", 5, 10, 5);
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                freeFightSession.getSessionReplay().addMessage(LogType.ARENA, "The session starts in " + count + "second.");
            }
            case 0 -> {

                EnemyTitle enemyTitleForPlayer1 = new EnemyTitle(player2.getName());
                EnemyTitle enemyTitleForPlayer2 = new EnemyTitle(player1.getName());

                PlayerUtils.sendTitle(
                        player1,
                        StartGameTitle.getInstance().getMessage(player1),
                        enemyTitleForPlayer1.getMessage(player1),
                        5, 20, 5);
                PlayerUtils.sendTitle(
                        player2,
                        StartGameTitle.getInstance().getMessage(player2),
                        enemyTitleForPlayer2.getMessage(player2),
                        5, 20, 5);

                player1.playSound(player1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0F, 100F);

                freeFightSession.setDamageAble(true);
                freeFightSession.getSessionReplay().addMessage(LogType.ARENA, "The session is started!");

                cancel();
            }
        }
        count--;
    }
}
