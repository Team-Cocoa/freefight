package kr.teamcocoa.freefight.items.lobby;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.translation.items.ChallengerTitle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Singleton
public class ChallengeItem extends AbstractItem {

    private ChallengerTitle challengerTitle;

    @Inject
    private ChallengeItem(ChallengerTitle challengerTitle) {
        super(Material.DIAMOND_SWORD);
        this.challengerTitle = challengerTitle;
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, challengerTitle.getMessage(player));
        return itemStack;
    }
}
