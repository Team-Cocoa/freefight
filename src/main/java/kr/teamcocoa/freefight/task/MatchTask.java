package kr.teamcocoa.freefight.task;

import kr.teamcocoa.core.bukkit.utils.PacketUtils;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.translation.actions.RemainTimeAction;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.MessageFormat;

@Getter
public class MatchTask extends BukkitRunnable {

    private FreeFightSession session;
    private int time;

    private FreeFightPlayer p1;
    private FreeFightPlayer p2;

    private int maximumRunningTime;
    private int runningTimeElapsed;

    @Setter
    private boolean hitted;

    public MatchTask(FreeFightSession session) {
        this.session = session;
        this.time = switch (session.getKits()) {
            case ONLYSWORD -> 3 * 60;
            case SHIELD -> 5 * 60;
            case DIAMOND_POT -> 10 * 60;
            case NETHERITE_POT -> 10 * 60;
            default -> throw new IllegalStateException();
        };
        this.p1 = session.getFreeFightPlayer1();
        this.p2 = session.getFreeFightPlayer2();

        this.maximumRunningTime = switch (session.getKits()) {
            case ONLYSWORD -> 20;
            case SHIELD, DIAMOND_POT -> 30;
            case NETHERITE_POT -> 40;
            default -> throw new IllegalStateException();
        };

        this.runningTimeElapsed = 0;
        this.hitted = false;
    }

    @Override
    public void run() {

        if(!session.isRunning()) {
            cancel();
            return;
        }

        RemainTimeAction remainTimeAction = new RemainTimeAction(time);
        PacketUtils.sendBar(p1.getPlayer(), remainTimeAction.getMessage(p1.getPlayer()));
        PacketUtils.sendBar(p2.getPlayer(), remainTimeAction.getMessage(p2.getPlayer()));

        if(time == 0) {
            stop();
        }

        runningTimeElapsed = hitted ? 0 : runningTimeElapsed + 1;
        hitted = false;

        if(maximumRunningTime - runningTimeElapsed == 4) {
            p1.getPlayer().sendMessage("Warning! This game will be ended in 5 seconds since no one is fighting!");
            p2.getPlayer().sendMessage("Warning! This game will be ended in 5 seconds since no one is fighting!");
        }

        if(maximumRunningTime - runningTimeElapsed == -1) {
            stop();
        }

        time--;

        Bukkit.getLogger().info(MessageFormat.format(
                "MatchTask({0}, {1}), maximumRunningTime {2} runningTimeElapsed {3}",
                p1.getPlayer().getName(),
                p2.getPlayer().getName(),
                maximumRunningTime,
                runningTimeElapsed));

    }

    private void stop() {
        if(p1.getDamageOut() > p2.getDamageOut()) {
            session.stop(p2);
        }
        else if (p1.getDamageOut() < p2.getDamageOut()) {
            session.stop(p1);
        }
        else {
            session.stop(null);
        }
        cancel();
    }

}
