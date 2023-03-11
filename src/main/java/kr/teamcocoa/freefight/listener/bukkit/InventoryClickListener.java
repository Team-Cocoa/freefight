package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.freefight.gui.KitSelectGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        KitSelectGUI.getInstance().onClick(e);
    }

}
