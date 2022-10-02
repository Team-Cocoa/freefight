package kr.teamcocoa.freefight.task;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class PearlCoolDownTask extends BukkitRunnable {

    private FreeFightPlayer freeFightPlayer;
    private Player player;

    private int count = 16;

    public PearlCoolDownTask(FreeFightPlayer freeFightPlayer) {
        this.freeFightPlayer = freeFightPlayer;
        this.player = freeFightPlayer.getPlayer();
    }


    @Override
    public void run() {
        player.setLevel(count);
        if(count == 0) {
            freeFightPlayer.setPearlThrowable(true);
            cancel();
        }
        count--;
    }
}
