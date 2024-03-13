package kr.teamcocoa.freefight.listener.bukkit;

import dev.derklaro.aerogel.Inject;
import kr.teamcocoa.freefight.items.lobby.KillEffectItem;
import kr.teamcocoa.freefight.items.lobby.KitSelectItem;
import kr.teamcocoa.freefight.items.lobby.SettingItem;
import kr.teamcocoa.freefight.items.lobby.SpectateItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

public class PlayerInteractListener implements Listener {

    @Inject
    private static KitSelectItem kitSelectItem;

    @Inject
    private static SpectateItem spectateItem;

    @Inject
    private static KillEffectItem killEffectItem;

    @Inject
    private static SettingItem settingItem;

    private final List<Material> blockedItemList = List.of(
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
        kitSelectItem.onClick(e);
        spectateItem.onClick(e);
        killEffectItem.onClick(e);
        settingItem.onClick(e);
    }

}
