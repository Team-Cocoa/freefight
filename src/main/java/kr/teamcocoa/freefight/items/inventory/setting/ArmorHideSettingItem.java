package kr.teamcocoa.freefight.items.inventory.setting;

import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.items.ClickAble;
import kr.teamcocoa.freefight.settings.FreeFightSetting;
import kr.teamcocoa.freefight.translation.items.settings.HideArmorSettingItemTitle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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

        return itemStack;
    }

    @Override
    public void onClickInInventory(InventoryClickEvent e) {

    }
}
