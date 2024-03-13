package kr.teamcocoa.freefight.gui;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.items.inventory.setting.ArmorHideSettingItem;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.settings.FreeFightSetting;
import kr.teamcocoa.freefight.translation.inventories.SettingInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

@Singleton
public class SettingGUI extends AbstractGUI {

    private ArmorHideSettingItem armorHideSettingItem;

    @Inject
    private SettingGUI(
            SettingInventory settingInventory,
            ArmorHideSettingItem armorHideSettingItem
    ) {
        super(1 * 9, settingInventory);
        this.armorHideSettingItem = armorHideSettingItem;
        fillInventory();
    }

    @Override
    public void openInventory(Player player) {
        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        FreeFightSetting setting = freeFightPlayer.getSettings();

        Inventory newInventory = Bukkit.createInventory(null, getSize(), Component.text(getTitle().getMessage(player)));
        newInventory.setContents(getInventory().getContents());
        newInventory.setItem(0, armorHideSettingItem.toItemStack(player, setting));

        player.openInventory(newInventory);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        try {
            if(!(checkItemStack(e) && checkThisInventoryClicked(e))) {
                return;
            }
            Player player = ((Player) e.getWhoClicked());

            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

            FreeFightSetting settings = freeFightPlayer.getSettings();

            switch (e.getSlot()) {
                case 0 -> {
                    armorHideSettingItem.onClickInInventory(e, settings);
                }
            }
            e.setCancelled(true);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
