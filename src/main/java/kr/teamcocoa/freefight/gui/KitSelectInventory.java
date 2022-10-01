package kr.teamcocoa.freefight.gui;

import kr.teamcocoa.freefight.items.inventory.icon.IconOnlySwordItem;
import kr.teamcocoa.freefight.items.inventory.icon.IconShieldItem;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.Kits;
import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class KitSelectInventory extends AbstractGUI {

    private static KitSelectInventory instance;

    public static KitSelectInventory getInstance() {
        if (instance == null) {
            instance = new KitSelectInventory();
        }
        return instance;
    }

    private KitSelectInventory() {
        super(1 * 9, "");
        fillInventory();
    }

    @Override
    public void openInventory(Player player) {
        Inventory newInventory = Bukkit.createInventory(null, getSize(), Component.text(getTitle()));
        newInventory.setContents(getInventory().getContents());
        newInventory.setItem(2, IconOnlySwordItem.getInstance().toItemStack(player));
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
                if(StringUtils.componentEquals(itemStack.displayName(), "&e&lOnlySword")) {
                    freeFightPlayer.changeKit(Kits.ONLYSWORD);
                    player.closeInventory();
                    player.sendMessage(StringUtils.color(
                            FreeFight.getPrefix() + "&aYour kit has been changed to &e" + Kits.getNameByEnum(Kits.ONLYSWORD) + "&a !"
                    ));
                }
                if(StringUtils.componentEquals(itemStack.displayName(), "&e&lShieldPvP")) {
                    freeFightPlayer.changeKit(Kits.SHIELD);
                    player.closeInventory();
                    player.sendMessage(StringUtils.color(
                            FreeFight.getPrefix() + "&aYour kit has been changed to &e" + Kits.getNameByEnum(Kits.SHIELD) + "&a !"
                    ));
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
