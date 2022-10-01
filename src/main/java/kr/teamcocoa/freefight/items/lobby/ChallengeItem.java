package kr.teamcocoa.freefight.items.lobby;

import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChallengeItem extends AbstractItem {

    private static ChallengeItem instance;

    public static ChallengeItem getInstance() {
        if (instance == null) {
            instance = new ChallengeItem();
        }
        return instance;
    }

    private ChallengeItem() {
        super(Material.DIAMOND_SWORD);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(StringUtils.color("&6&lChallenger")));

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
