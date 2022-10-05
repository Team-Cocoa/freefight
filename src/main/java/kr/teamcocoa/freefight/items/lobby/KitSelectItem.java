package kr.teamcocoa.freefight.items.lobby;

import kr.teamcocoa.freefight.gui.KitSelectInventory;
import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.items.ClickAble;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.utils.ItemUtils;
import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        ItemUtils.name(itemStack, "&6&lKit Select");
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

        if(StringUtils.componentEquals(player.getInventory().getItemInMainHand().getItemMeta().displayName(), "&6&lKit Select")) {
            KitSelectInventory.getInstance().openInventory(player);
        }
    }
}
