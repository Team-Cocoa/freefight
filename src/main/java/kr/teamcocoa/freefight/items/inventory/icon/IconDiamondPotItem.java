package kr.teamcocoa.freefight.items.inventory.icon;

import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.items.AbstractItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IconDiamondPotItem extends AbstractItem {

    private static IconDiamondPotItem instance;

    public static IconDiamondPotItem getInstance() {
        if (instance == null) {
            instance = new IconDiamondPotItem();
        }
        return instance;
    }

    private IconDiamondPotItem() {
        super(Material.DIAMOND_CHESTPLATE);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack,"&e&lDiamond Pot");
        return itemStack;
    }
}
