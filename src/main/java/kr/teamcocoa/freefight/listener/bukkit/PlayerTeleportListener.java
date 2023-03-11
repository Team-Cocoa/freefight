package kr.teamcocoa.freefight.listener.bukkit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if(e.getTo().getBlock().getType() == Material.BARRIER) {
                Player player = e.getPlayer();
                e.setCancelled(true);
                player.teleport(e.getTo().toVector().multiply(-1.0).toLocation(e.getTo().getWorld()));
            }
        }
    }

}
