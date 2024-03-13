package kr.teamcocoa.freefight.items.lobby;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.core.bukkit.utils.ComponentUtils;
import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.items.ClickAble;
import me.nucha.swkilleffect.SWKillEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@Singleton
public class KillEffectItem extends AbstractItem implements ClickAble {

    private KillEffectItem() {
        super(Material.GOLDEN_SWORD);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, "&6&lKill Effects");
        return itemStack;
    }

    @Override
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (!player.getInventory().getItemInMainHand().hasItemMeta()) {
            return;
        }

        if (ComponentUtils.componentEquals(player.getInventory().getItemInMainHand().getItemMeta().displayName(), "&6&lKill Effects")) {
            if (!player.hasPermission("teamcocoa.killeffect")) {
                player.sendMessage("Invalid access.");
                return;
            }
            SWKillEffect.getInstance().getGuiKillEffectSelector().open(player);
        }
    }
}
