package kr.teamcocoa.freefight.items.lobby;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.core.bukkit.utils.ComponentUtils;
import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.gui.KitSelectGUI;
import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.items.ClickAble;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.translation.items.KitSelectTitle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@Singleton
public class KitSelectItem extends AbstractItem implements ClickAble {

    private KitSelectGUI kitSelectGUI;
    private KitSelectTitle kitSelectTitle;

    @Inject
    private KitSelectItem(
            KitSelectGUI kitSelectGUI,
            KitSelectTitle kitSelectTitle) {
        super(Material.ENDER_EYE);
        this.kitSelectGUI = kitSelectGUI;
        this.kitSelectTitle = kitSelectTitle;
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, kitSelectTitle.getMessage(player));
        return itemStack;
    }

    @Override
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
        if(freeFightPlayer == null) {
            return;
        }

        if(!player.getInventory().getItemInMainHand().hasItemMeta()) {
            return;
        }

        if(ComponentUtils.componentEquals(player.getInventory().getItemInMainHand().getItemMeta().displayName(), kitSelectTitle.getMessage(player))) {
            kitSelectGUI.openInventory(player);
        }
    }
}
