package kr.teamcocoa.freefight.listener;

import kr.teamcocoa.freefight.items.lobby.KitSelectItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

public class PlayerInteractListener implements Listener {

    private List<Material> blockedItemList = Arrays.asList(
            Material.ENDER_EYE
    );

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.hasItem()) {
            if(blockedItemList.contains(e.getItem().getType())) {
                e.setCancelled(true);
            }
            if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()) {
                handleItemsClick(e);
            }
        }
    }

    private void handleItemsClick(PlayerInteractEvent e) {
        KitSelectItem.getInstance().onClick(e);
    }

}
