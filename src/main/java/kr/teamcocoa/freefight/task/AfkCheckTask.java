package kr.teamcocoa.freefight.task;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class AfkCheckTask extends BukkitRunnable {

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();

        for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
            if(freeFightPlayer.getState() == GameState.INGAME) {
                continue;
            }

            Player player = freeFightPlayer.getPlayer();

            boolean isStaff = player.hasPermission("teamcooca.staff");

            if(isStaff) {
                continue;
            }

            boolean isPremium = player.hasPermission("teamcocoa.premium");

            long lastMovingTime = freeFightPlayer.getLastMovingTime();
            long lastStateChangeTime = freeFightPlayer.getLastStateChangeTime();

            if(currentTime - lastStateChangeTime > TimeUnit.MINUTES.toMillis(isPremium ? 30 : 10)) {
                player.kick();
            }

            if(currentTime - lastMovingTime > TimeUnit.MINUTES.toMillis(isPremium ? 15 : 5)) {
                player.kick();
            }

        }
    }
}
