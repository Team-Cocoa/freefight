package kr.teamcocoa.freefight.listener;

import kr.teamcocoa.freefight.gui.KitSelectInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        KitSelectInventory.getInstance().onClick(e);
    }

}
