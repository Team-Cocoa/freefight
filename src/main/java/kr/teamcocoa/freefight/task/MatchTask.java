package kr.teamcocoa.freefight.task;

import kr.teamcocoa.core.bukkit.utils.PacketUtils;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.translation.actions.RemainTimeAction;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class MatchTask extends BukkitRunnable {

    private FreeFightSession session;
    private int time;

    private FreeFightPlayer p1;
    private FreeFightPlayer p2;

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

        time--;

    }
}
