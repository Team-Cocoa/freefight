package kr.teamcocoa.freefight.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        e.setCancelled(true);

    }

}
