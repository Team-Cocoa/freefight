package kr.teamcocoa.freefight.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class PlayerPickupArrowListener implements Listener {

    @EventHandler
    public void onPick(PlayerPickupArrowEvent e) {
        e.setCancelled(true);
    }

}
