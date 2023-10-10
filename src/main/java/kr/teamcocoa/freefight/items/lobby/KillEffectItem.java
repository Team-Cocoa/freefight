package kr.teamcocoa.freefight.items.lobby;

import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.items.ClickAble;
import kr.teamcocoa.freefight.utils.ItemUtils;
import kr.teamcocoa.freefight.utils.StringUtils;
import me.nucha.swkilleffect.SWKillEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class KillEffectItem extends AbstractItem implements ClickAble {

    private static KillEffectItem instance;

    public static KillEffectItem getInstance() {
        if(instance == null) {
            instance = new KillEffectItem();
        }
        return instance;
    }

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

        if(!player.hasPermission("teamcocoa.killeffect")) {
            player.sendMessage("Invalid access.");
            return;
        }

        if(!player.getInventory().getItemInMainHand().hasItemMeta()) {
            return;
        }

        if(StringUtils.componentEquals(player.getInventory().getItemInMainHand().getItemMeta().displayName(), "&6&lKill Effects")) {
            SWKillEffect.getInstance().getGuiKillEffectSelector().open(player);
        }

    }
}
