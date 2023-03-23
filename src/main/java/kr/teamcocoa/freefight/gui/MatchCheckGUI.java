package kr.teamcocoa.freefight.gui;

import kr.teamcocoa.freefight.session.result.SessionResult;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class MatchCheckGUI extends AbstractGUI {

    private SessionResult sessionResult;

    public MatchCheckGUI(SessionResult sessionResult) {
        // TODO : 인벤토리 이름 translation class instance 필요
        super(1 * 9, null);
        this.sessionResult = sessionResult;
        fillInventory();
    }

    @Override
    public void openInventory(Player player) {
        Inventory newInventory = Bukkit.createInventory(null, getSize(), Component.empty());
        newInventory.setContents(getInventory().getContents());
        sessionResult.initInventory(player, newInventory);
    }

    @Override
    public void onClick(InventoryClickEvent e) {

    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
