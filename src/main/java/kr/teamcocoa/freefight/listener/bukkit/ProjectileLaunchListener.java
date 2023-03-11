package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import kr.teamcocoa.freefight.task.PearlCoolDownTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if(e.getEntity().getShooter() instanceof Player player) {
            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
            if (freeFightPlayer == null) {
                return;
            }

            if (e.getEntity() instanceof EnderPearl) {
                if (freeFightPlayer.isPearlThrowable()) {
                    freeFightPlayer.setPearlThrowable(false);
                    new PearlCoolDownTask(freeFightPlayer).runTaskTimer(FreeFight.getInstance(), 0L, 20L);
                } else {
                    e.setCancelled(true);
                }
            }

            FreeFightSession freeFightSession = SessionManager.getSession(freeFightPlayer);
            if (freeFightSession == null) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(onlinePlayer != player) {
                        onlinePlayer.hideEntity(FreeFight.getInstance(), e.getEntity());
                    }
                }
            }
            else {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(freeFightSession.getFreeFightPlayer1().getPlayer() != onlinePlayer &&
                            freeFightSession.getFreeFightPlayer2().getPlayer() != onlinePlayer) {
                        onlinePlayer.hideEntity(FreeFight.getInstance(), e.getEntity());
                    }
                }
            }


        }
    }

}
