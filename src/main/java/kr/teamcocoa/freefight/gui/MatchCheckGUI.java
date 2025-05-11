package kr.teamcocoa.freefight.gui;

import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.session.SessionResult;
import kr.teamcocoa.freefight.translation.inventories.ResultInventory;
import kr.teamcocoa.freefight.translation.items.MatchHeadTitle;
import kr.teamcocoa.freefight.translation.lores.MatchInfoLore;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MatchCheckGUI extends AbstractGUI {

    private static final String INFO_HEAD_VALUE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWYzM2U3YmIxMjU2YTEyYjVjODhlNzA1ZjIxMjc0ZmQ4NjE4YmJkZTkzYzBkZDNlMjJkOWRiY2YwYjNhMTJiMyJ9fX0=";

    private static MatchCheckGUI instance;

    public static MatchCheckGUI getInstance() {
        if(instance == null) {
            instance = new MatchCheckGUI(null);
        }
        return instance;
    }

    private SessionResult sessionResult;

    public MatchCheckGUI(SessionResult sessionResult) {
        super(1 * 9, ResultInventory.getInstance());
        this.sessionResult = sessionResult;
        fillInventory();
    }

    @Override
    public void openInventory(Player player) {
        Inventory newInventory = Bukkit.createInventory(null, getSize(), Component.text(getTitle().getMessage(player)));
        newInventory.setContents(getInventory().getContents());
        sessionResult.initInventory(player, newInventory);

        MatchHeadTitle matchHeadTitle = new MatchHeadTitle(sessionResult.getId());
        MatchInfoLore matchInfoLore = new MatchInfoLore(sessionResult, player);

        ItemStack itemStack = ItemUtils.getCustomHead(INFO_HEAD_VALUE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(matchHeadTitle.getMessage(player)));
        itemMeta.lore(matchInfoLore.getLoreMessage(player));

        itemStack.setItemMeta(itemMeta);

        newInventory.setItem(4, itemStack);

        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> player.openInventory(newInventory));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if(checkThisInventoryClicked(e)) {
            e.setCancelled(true);
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }

}
