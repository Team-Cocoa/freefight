package kr.teamcocoa.freefight.items;

import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftMetaBlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChallengeItem extends AbstractItem {

    public ChallengeItem() {
        super(Material.DIAMOND_SWORD);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(StringUtils.color("&6&lChallenger")));
//        itemStack.getItemMeta().setDisplayName(StringUtils.color());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
