package kr.teamcocoa.freefight.gui;

import kr.teamcocoa.freefight.utils.StringUtils;
import kr.teamcocoa.freefight.utils.Utils;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter(AccessLevel.PROTECTED)
public abstract class AbstractGUI {

    private Inventory inventory;
    private int size;
    private String title;

    protected AbstractGUI(int size, String title) {
        this.size = size;
        this.title = StringUtils.color(title);
        this.inventory = Bukkit.createInventory(null, size, Component.text(StringUtils.color(this.title)));
    }

    private ItemStack background = Utils.getBackground();

    public abstract void openInventory(Player player);
    public abstract void onClick(InventoryClickEvent e);
    public abstract void onClose(InventoryCloseEvent e);

    protected final void fillInventory() {
        for(int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, background);
        }
    }

    protected final boolean checkThisInventoryClicked(InventoryClickEvent e) {
        return StringUtils.componentEquals(e.getView().title(), title);
    }

    protected final boolean checkItemStack(InventoryClickEvent e) {
        return e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta();
    }


}
