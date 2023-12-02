package kr.teamcocoa.freefight.task;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ConcurrentModificationException;
import java.util.concurrent.TimeUnit;

public class AfkCheckTask extends BukkitRunnable {

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();

        FreeFightPlayerManager.getPlayerTable().forEach((player, freeFightPlayer) -> {
            if(freeFightPlayer.getState() == GameState.INGAME) {
                return;
            }

            boolean isStaff = player.hasPermission("teamcooca.staff");

            if(isStaff) {
                return;
            }

            boolean isPremium = player.hasPermission("teamcocoa.premium");

            long lastMovingTime = freeFightPlayer.getLastMovingTime();
            long lastStateChangeTime = freeFightPlayer.getLastStateChangeTime();

            if(currentTime - lastStateChangeTime > TimeUnit.MINUTES.toMillis(isPremium ? 30 : 10)) {
                try {
                    player.kick();
                }
                catch (ConcurrentModificationException e) {

                }
            }

            if(currentTime - lastMovingTime > TimeUnit.MINUTES.toMillis(isPremium ? 15 : 5)) {
                try {
                    player.kick();
                }
                catch (ConcurrentModificationException e) {

                }
            }
        });

    }
}
