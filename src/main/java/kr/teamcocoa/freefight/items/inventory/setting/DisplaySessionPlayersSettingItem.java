package kr.teamcocoa.freefight.items.inventory.setting;

import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.gui.SettingGUI;
import kr.teamcocoa.freefight.settings.FreeFightSetting;
import kr.teamcocoa.freefight.translation.items.settings.DisableSettingOption;
import kr.teamcocoa.freefight.translation.items.settings.DisplaySessionPlayersItemTitle;
import kr.teamcocoa.freefight.translation.items.settings.EnableSettingOption;
import kr.teamcocoa.freefight.translation.lores.settings.DisplaySessionPlayersItemLore;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

public class DisplaySessionPlayersSettingItem extends SettingInventoryIcon {

    private static DisplaySessionPlayersSettingItem instance;

    public static DisplaySessionPlayersSettingItem getInstance() {
        if(instance == null) {
            instance = new DisplaySessionPlayersSettingItem();
        }
        return instance;
    }

    private DisplaySessionPlayersSettingItem() {
        super(Material.SPYGLASS);
    }

    @Override
    public ItemStack toItemStack(Player player, FreeFightSetting setting) {
        ItemStack itemStack = new ItemStack(getMaterial());

        ItemUtils.name(itemStack, DisplaySessionPlayersItemTitle.getInstance().getMessage(player));
        List<Component> lore = DisplaySessionPlayersItemLore.getInstance().getLoreMessage(player);
        lore.add(Component.empty());
        lore.add(Component.text(
                setting.isDisplaySessionPlayers()
                        ? EnableSettingOption.getInstance().getMessage(player)
                        : DisableSettingOption.getInstance().getMessage(player)));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onClickInInventory(InventoryClickEvent e, FreeFightSetting settings) {
        Player player = ((Player) e.getWhoClicked());

        settings.setDisplaySessionPlayers(!settings.isDisplaySessionPlayers());

        player.closeInventory();

        SettingGUI.getInstance().openInventory(player);
    }
}
