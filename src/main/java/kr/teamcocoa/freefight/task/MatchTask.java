package kr.teamcocoa.freefight.task;

import kr.teamcocoa.freefight.session.FreeFightSession;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class MatchTask extends BukkitRunnable {

    private FreeFightSession session;
    private int time;

    public MatchTask(FreeFightSession session) {
        this.session = session;
        this.time = switch (session.getKits()) {
            case ONLYSWORD -> 3 * 60;
            case SHIELD -> 5 * 60;
            case DIAMOND_POT -> 10 * 60;
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public void run() {

        if(!session.isRunning()) {
            cancel();
            return;
        }

        if(time == 0) {

        }

        time--;

    }
}
