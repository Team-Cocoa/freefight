package kr.teamcocoa.freefight.items.inventory.icon;

import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.items.AbstractItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IconOnlySwordItem extends AbstractItem {

    private static IconOnlySwordItem instance;

    public static IconOnlySwordItem getInstance() {
        if (instance == null) {
            instance = new IconOnlySwordItem();
        }
        return instance;
    }

    private IconOnlySwordItem() {
        super(Material.DIAMOND_SWORD);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, "&e&lOnlySword");
        return itemStack;
    }
}
