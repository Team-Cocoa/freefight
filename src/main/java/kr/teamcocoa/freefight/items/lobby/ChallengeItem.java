package kr.teamcocoa.freefight.items.lobby;

import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.translation.items.ChallengerTitle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        ItemUtils.name(itemStack, ChallengerTitle.getInstance().getMessage(player));
        return itemStack;
    }
}
