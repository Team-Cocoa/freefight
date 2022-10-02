package kr.teamcocoa.freefight.items.inventory.icon;

import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(StringUtils.color(
                "&e&lDiamond Pot")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
