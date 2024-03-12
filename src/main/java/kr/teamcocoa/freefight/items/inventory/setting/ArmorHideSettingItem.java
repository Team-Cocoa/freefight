package kr.teamcocoa.freefight.items.inventory.setting;

import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.gui.SettingGUI;
import kr.teamcocoa.freefight.settings.FreeFightSetting;
import kr.teamcocoa.freefight.translation.items.settings.DisableSettingOption;
import kr.teamcocoa.freefight.translation.items.settings.EnableSettingOption;
import kr.teamcocoa.freefight.translation.items.settings.HideArmorSettingItemTitle;
import kr.teamcocoa.freefight.translation.lores.settings.HideArmorSettingItemLore;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ArmorHideSettingItem extends SettingInventoryIcon {

    private static ArmorHideSettingItem instance;

    public static ArmorHideSettingItem getInstance() {
        if(instance == null) {
            instance = new ArmorHideSettingItem();
        }
        return instance;
    }

    private ArmorHideSettingItem() {
        super(Material.DIAMOND_CHESTPLATE);
    }

    @Override
    public ItemStack toItemStack(Player player, FreeFightSetting setting) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, HideArmorSettingItemTitle.getInstance().getMessage(player));
        List<Component> lore = HideArmorSettingItemLore.getInstance().getLoreMessage(player);
        lore.add(Component.empty());
        lore.add(Component.text(
                setting.isHideArmor()
                        ? EnableSettingOption.getInstance().getMessage(player)
                        : DisableSettingOption.getInstance().getMessage(player)));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onClickInInventory(InventoryClickEvent e, FreeFightSetting settings) {
        Player player = (Player) e.getWhoClicked();

        settings.setHideArmor(!settings.isHideArmor());

        player.closeInventory();

        SettingGUI.getInstance().openInventory(player);
    }
}
