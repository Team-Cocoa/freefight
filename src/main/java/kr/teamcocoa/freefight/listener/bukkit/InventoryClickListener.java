package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.freefight.gui.KitSelectGUI;
import kr.teamcocoa.freefight.gui.MatchCheckGUI;
import kr.teamcocoa.freefight.translation.inventories.ResultInventory;
import kr.teamcocoa.freefight.utils.StringUtils;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftHumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        KitSelectGUI.getInstance().onClick(e);
        MatchCheckGUI.getInstance().onClick(e);
    }

}
