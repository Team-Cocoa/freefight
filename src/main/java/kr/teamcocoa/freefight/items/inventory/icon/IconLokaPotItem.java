package kr.teamcocoa.freefight.items.inventory.icon;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.items.AbstractItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Singleton
public class IconLokaPotItem extends AbstractItem {

    private IconLokaPotItem() {
        super(Material.CLOCK);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, "&e&lLoka Pot");
        return itemStack;
    }

}
