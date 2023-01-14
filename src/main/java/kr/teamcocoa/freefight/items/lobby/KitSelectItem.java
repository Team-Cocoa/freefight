package kr.teamcocoa.freefight.items.lobby;

import kr.teamcocoa.freefight.gui.KitSelectGUI;
import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.items.ClickAble;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.translation.items.KitSelectTitle;
import kr.teamcocoa.freefight.utils.ItemUtils;
import kr.teamcocoa.freefight.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class KitSelectItem extends AbstractItem implements ClickAble {

    private static KitSelectItem instance;

    public static KitSelectItem getInstance() {
        if (instance == null) {
            instance = new KitSelectItem();
        }
        return instance;
    }

    private KitSelectItem() {
        super(Material.ENDER_EYE);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, KitSelectTitle.getInstance().getMessage(player));
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

        if(StringUtils.componentEquals(player.getInventory().getItemInMainHand().getItemMeta().displayName(), KitSelectTitle.getInstance().getMessage(player))) {
            KitSelectGUI.getInstance().openInventory(player);
        }
    }
}
