package kr.teamcocoa.freefight.items.lobby;

import kr.teamcocoa.core.bukkit.utils.ComponentUtils;
import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.gui.KitSelectGUI;
import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.items.ClickAble;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.translation.items.SettingTitle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SettingItem extends AbstractItem implements ClickAble {

    private static SettingItem instance;

    public static SettingItem getInstance() {
        if(instance == null) {
            instance = new SettingItem();
        }
        return instance;
    }

    private SettingItem() {
        super(Material.COMPARATOR);
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

        if(ComponentUtils.componentEquals(
                player.getInventory().getItemInMainHand().getItemMeta().displayName(),
                SettingTitle.getInstance().getMessage(player))) {
            KitSelectGUI.getInstance().openInventory(player);
        }
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, SettingTitle.getInstance().getMessage(player));

        return itemStack;
    }
}
