package kr.teamcocoa.freefight.items.inventory.icon;

import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.items.AbstractItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IconShieldItem extends AbstractItem {

    private static IconShieldItem instance;

    public static IconShieldItem getInstance() {
        if (instance == null) {
            instance = new IconShieldItem();
        }
        return instance;
    }

    private IconShieldItem() {
        super(Material.SHIELD);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, "&e&lShieldPvP");
        return itemStack;
    }
}
