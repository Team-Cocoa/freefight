package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.freefight.gui.KitSelectGUI;
import kr.teamcocoa.freefight.gui.MatchCheckGUI;
import kr.teamcocoa.freefight.gui.SettingGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) {
            return;
        }

        KitSelectGUI.getInstance().onClick(e);
        MatchCheckGUI.getInstance().onClick(e);
        SettingGUI.getInstance().onClick(e);

        if(e.getClickedInventory().getType() == InventoryType.CRAFTING) {
            e.setCancelled(true);
        }

    }

}
