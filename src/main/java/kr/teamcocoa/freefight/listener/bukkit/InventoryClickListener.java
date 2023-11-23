package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.freefight.gui.KitSelectGUI;
import kr.teamcocoa.freefight.gui.MatchCheckGUI;
import kr.teamcocoa.freefight.translation.inventories.ResultInventory;
import kr.teamcocoa.core.utils.StringUtils;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftHumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        KitSelectGUI.getInstance().onClick(e);
        MatchCheckGUI.getInstance().onClick(e);

        if(e.getClickedInventory().getType() == InventoryType.CRAFTING) {
            e.setCancelled(true);
        }

    }

}
