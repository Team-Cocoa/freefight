package kr.teamcocoa.freefight.gui;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.core.bukkit.utils.ComponentUtils;
import kr.teamcocoa.freefight.items.inventory.icon.*;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.translation.inventories.KitSelectInventories;
import kr.teamcocoa.freefight.translation.messages.KitChangeMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


@Singleton
public class KitSelectGUI extends AbstractGUI {
    private IconOnlySwordItem iconOnlySwordItem;
    private IconDiamondPotItem iconDiamondPotItem;
    private IconNetheritePotItem iconNetheritePotItem;
    private IconShieldItem iconShieldItem;
    private IconLokaPotItem iconLokaPotItem;

    @Inject
    private KitSelectGUI(
            KitSelectInventories kitSelectInventories,
            IconOnlySwordItem iconOnlySwordItem,
            IconDiamondPotItem iconDiamondPotItem,
            IconNetheritePotItem iconNetheritePotItem,
            IconShieldItem iconShieldItem,
            IconLokaPotItem iconLokaPotItem
    ) {
        super(1 * 9, kitSelectInventories);
        this.iconOnlySwordItem = iconOnlySwordItem;
        this.iconDiamondPotItem = iconDiamondPotItem;
        this.iconNetheritePotItem = iconNetheritePotItem;
        this.iconShieldItem = iconShieldItem;
        this.iconLokaPotItem = iconLokaPotItem;
        fillInventory();
    }

    @Override
    public void openInventory(Player player) {
        Inventory newInventory = Bukkit.createInventory(null, getSize(), Component.text(getTitle().getMessage(player)));
        newInventory.setContents(getInventory().getContents());
        newInventory.setItem(0, iconOnlySwordItem.toItemStack(player));
        newInventory.setItem(2, iconDiamondPotItem.toItemStack(player));
        newInventory.setItem(4, iconNetheritePotItem.toItemStack(player));
        newInventory.setItem(6, iconShieldItem.toItemStack(player));
        newInventory.setItem(8, iconLokaPotItem.toItemStack(player));
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
                if(ComponentUtils.componentEquals(itemStack.getItemMeta().displayName(), "&e&lOnlySword")) {
                    freeFightPlayer.changeKit(Kits.ONLYSWORD);
                    player.closeInventory();
                    KitChangeMessage kitChangeMessage = new KitChangeMessage(Kits.ONLYSWORD);
                    player.sendMessage(kitChangeMessage.getMessage(player));
                }
                if(ComponentUtils.componentEquals(itemStack.getItemMeta().displayName(), "&e&lDiamond Pot")) {
                    freeFightPlayer.changeKit(Kits.DIAMOND_POT);
                    player.closeInventory();
                    KitChangeMessage kitChangeMessage = new KitChangeMessage(Kits.DIAMOND_POT);
                    player.sendMessage(kitChangeMessage.getMessage(player));
                }
                if(ComponentUtils.componentEquals(itemStack.getItemMeta().displayName(), "&e&lNetherite Pot")) {
                    freeFightPlayer.changeKit(Kits.NETHERITE_POT);
                    player.closeInventory();
                    KitChangeMessage kitChangeMessage = new KitChangeMessage(Kits.NETHERITE_POT);
                    player.sendMessage(kitChangeMessage.getMessage(player));
                }
                if(ComponentUtils.componentEquals(itemStack.getItemMeta().displayName(), "&e&lShieldPvP")) {
                    freeFightPlayer.changeKit(Kits.SHIELD);
                    player.closeInventory();
                    KitChangeMessage kitChangeMessage = new KitChangeMessage(Kits.SHIELD);
                    player.sendMessage(kitChangeMessage.getMessage(player));
                }
                if(ComponentUtils.componentEquals(itemStack.getItemMeta().displayName(), "&e&lLoka Pot")) {
                    freeFightPlayer.changeKit(Kits.LOKA_POT);
                    player.closeInventory();
                    KitChangeMessage kitChangeMessage = new KitChangeMessage(Kits.LOKA_POT);
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
