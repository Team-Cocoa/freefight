package kr.teamcocoa.freefight.listener.bukkit;

import dev.derklaro.aerogel.Inject;
import kr.teamcocoa.freefight.gui.KitSelectGUI;
import kr.teamcocoa.freefight.gui.MatchCheckGUI;
import kr.teamcocoa.freefight.gui.SettingGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener {

    @Inject
    private static KitSelectGUI kitSelectGUI;

    @Inject
    private static SettingGUI settingGUI;

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) {
            return;
        }

        kitSelectGUI.onClick(e);
        MatchCheckGUI.INSTANCE.onClick(e);
        settingGUI.onClick(e);

        if(e.getClickedInventory().getType() == InventoryType.CRAFTING) {
            e.setCancelled(true);
        }

    }

}
