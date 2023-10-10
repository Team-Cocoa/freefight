package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.freefight.items.lobby.KillEffectItem;
import kr.teamcocoa.freefight.items.lobby.KitSelectItem;
import kr.teamcocoa.freefight.items.lobby.SpectateItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
            if(e.getItem().hasItemMeta()) {
                handleItemsClick(e);
            }
        }
    }

    private void handleItemsClick(PlayerInteractEvent e) {
        KitSelectItem.getInstance().onClick(e);
        SpectateItem.getInstance().onClick(e);
        KillEffectItem.getInstance().onClick(e);
    }

}
