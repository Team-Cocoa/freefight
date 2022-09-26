package kr.teamcocoa.freefight.items;

import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChallengeItem extends AbstractItem {

    public ChallengeItem() {
        super(Material.DIAMOND_SWORD);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        itemStack.getItemMeta().displayName(Component.text(
                StringUtils.color("&6&lChallenger")
        ));
        return itemStack;
    }
}
