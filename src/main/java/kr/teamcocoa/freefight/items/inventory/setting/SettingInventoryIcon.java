package kr.teamcocoa.freefight.items.inventory.setting;

import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.settings.FreeFightSetting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class SettingInventoryIcon extends AbstractItem {

    protected SettingInventoryIcon(Material material) {
        super(material);
    }

    public abstract ItemStack toItemStack(Player player, FreeFightSetting setting);

    @Override
    public ItemStack toItemStack(Player player) {
        return null;
    }

    public abstract void onClickInInventory(InventoryClickEvent e);

}
