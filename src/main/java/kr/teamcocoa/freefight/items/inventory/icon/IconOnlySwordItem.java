package kr.teamcocoa.freefight.items.inventory.icon;

import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(StringUtils.color("&e&lOnlySword")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
