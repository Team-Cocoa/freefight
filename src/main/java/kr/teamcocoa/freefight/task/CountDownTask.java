package kr.teamcocoa.freefight.task;

import dev.derklaro.aerogel.Inject;
import kr.teamcocoa.core.bukkit.utils.PacketUtils;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.replay.LogType;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.translation.titles.EnemyTitle;
import kr.teamcocoa.freefight.translation.titles.StartGameTitle;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class CountDownTask extends BukkitRunnable {

    @Inject
    private static StartGameTitle startGameTitle;

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
                PacketUtils.sendTitle(player1, "&e3", "", 5, 10, 5);
                PacketUtils.sendTitle(player2, "&e3", "", 5, 10, 5);
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                freeFightSession.getSessionReplay().addMessage(LogType.ARENA, "The session starts in " + count + "seconds.");
            }
            case 2 -> {
                PacketUtils.sendTitle(player1, "&c2", "", 5, 10, 5);
                PacketUtils.sendTitle(player2, "&c2", "", 5, 10, 5);
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                freeFightSession.getSessionReplay().addMessage(LogType.ARENA, "The session starts in " + count + "seconds.");
            }
            case 1 -> {
                PacketUtils.sendTitle(player1, "&41", "", 5, 10, 5);
                PacketUtils.sendTitle(player2, "&41", "", 5, 10, 5);
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0F, 100F);
                freeFightSession.getSessionReplay().addMessage(LogType.ARENA, "The session starts in " + count + "second.");
            }
            case 0 -> {

                EnemyTitle enemyTitleForPlayer1 = new EnemyTitle(player2.getName());
                EnemyTitle enemyTitleForPlayer2 = new EnemyTitle(player1.getName());

                PacketUtils.sendTitle(
                        player1,
                        startGameTitle.getMessage(player1),
                        enemyTitleForPlayer1.getMessage(player1),
                        5, 20, 5);
                PacketUtils.sendTitle(
                        player2,
                        startGameTitle.getMessage(player2),
                        enemyTitleForPlayer2.getMessage(player2),
                        5, 20, 5);

                player1.playSound(player1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0F, 100F);
                player2.playSound(player2.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0F, 100F);

                freeFightSession.setDamageAble(true);
                freeFightSession.getSessionReplay().addMessage(LogType.ARENA, "The session is started!");

                MatchTask matchTask = new MatchTask(freeFightSession);
                matchTask.runTaskTimer(FreeFight.getInstance(), 0L, 20L);
                freeFightSession.setMatchTask(matchTask);
                cancel();
            }
        }
        count--;
    }
}
