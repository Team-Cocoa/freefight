package kr.teamcocoa.freefight.items.inventory.icon;

import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IconNetheritePotItem extends AbstractItem {
    
    private static IconNetheritePotItem instance;

    public static IconNetheritePotItem getInstance() {
        if(instance == null) {
            instance = new IconNetheritePotItem();
        }
        return instance;
    }
    
    private IconNetheritePotItem() {
        super(Material.NETHERITE_SWORD);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, "&e&lNetherite Pot");
        return itemStack;
    }
    
}
