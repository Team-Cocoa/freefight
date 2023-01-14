package kr.teamcocoa.freefight.gui;

import kr.teamcocoa.freefight.items.inventory.icon.IconDiamondPotItem;
import kr.teamcocoa.freefight.items.inventory.icon.IconOnlySwordItem;
import kr.teamcocoa.freefight.items.inventory.icon.IconShieldItem;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.translation.inventories.KitSelectInventories;
import kr.teamcocoa.freefight.translation.messages.KitChangeMessage;
import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class KitSelectGUI extends AbstractGUI {

    private static KitSelectGUI instance;

    public static KitSelectGUI getInstance() {
        if (instance == null) {
            instance = new KitSelectGUI();
        }
        return instance;
    }

    private KitSelectGUI() {
        super(1 * 9, KitSelectInventories.getInstance());
        fillInventory();
    }

    @Override
    public void openInventory(Player player) {
        Inventory newInventory = Bukkit.createInventory(null, getSize(), Component.text(getTitle().getMessage(player)));
        newInventory.setContents(getInventory().getContents());
        newInventory.setItem(2, IconOnlySwordItem.getInstance().toItemStack(player));
        newInventory.setItem(4, IconDiamondPotItem.getInstance().toItemStack(player));
        newInventory.setItem(6, IconShieldItem.getInstance().toItemStack(player));
        player.openInventory(newInventory);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        try {
            if(checkItemStack(e) && checkThisInventoryClicked(e)) {
                Player player = (Player) e.getWhoClicked();
                FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

                if(freeFightPlayer == null) {
                    e.setCancelled(true);
                    player.closeInventory();
                    return;
                }

                ItemStack itemStack = e.getCurrentItem();
                if(StringUtils.componentEquals(itemStack.getItemMeta().displayName(), "&e&lOnlySword")) {
                    freeFightPlayer.changeKit(Kits.ONLYSWORD);
                    player.closeInventory();
                    KitChangeMessage kitChangeMessage = new KitChangeMessage(Kits.ONLYSWORD);
                    player.sendMessage(kitChangeMessage.getMessage(player));
                }
                if(StringUtils.componentEquals(itemStack.getItemMeta().displayName(), "&e&lDiamond Pot")) {
                    freeFightPlayer.changeKit(Kits.DIAMOND_POT);
                    player.closeInventory();
                    KitChangeMessage kitChangeMessage = new KitChangeMessage(Kits.DIAMOND_POT);
                    player.sendMessage(kitChangeMessage.getMessage(player));
                }
                if(StringUtils.componentEquals(itemStack.getItemMeta().displayName(), "&e&lShieldPvP")) {
                    freeFightPlayer.changeKit(Kits.SHIELD);
                    player.closeInventory();
                    KitChangeMessage kitChangeMessage = new KitChangeMessage(Kits.SHIELD);
                    player.sendMessage(kitChangeMessage.getMessage(player));
                }
                e.setCancelled(true);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
