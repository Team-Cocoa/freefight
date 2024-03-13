package kr.teamcocoa.freefight.items.inventory.setting;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.gui.SettingGUI;
import kr.teamcocoa.freefight.settings.FreeFightSetting;
import kr.teamcocoa.freefight.translation.items.settings.DisableSettingOption;
import kr.teamcocoa.freefight.translation.items.settings.EnableSettingOption;
import kr.teamcocoa.freefight.translation.items.settings.HideArmorSettingItemTitle;
import kr.teamcocoa.freefight.translation.lores.settings.HideArmorSettingItemLore;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Singleton
public class ArmorHideSettingItem extends SettingInventoryIcon {

    private SettingGUI settingGUI;
    private HideArmorSettingItemTitle hideArmorSettingItemTitle;
    private HideArmorSettingItemLore hideArmorSettingItemLore;
    private EnableSettingOption enableSettingOption;
    private DisableSettingOption disableSettingOption;


    @Inject
    private ArmorHideSettingItem(
            SettingGUI settingGUI,
            HideArmorSettingItemTitle hideArmorSettingItemTitle,
            HideArmorSettingItemLore hideArmorSettingItemLore,
            EnableSettingOption enableSettingOption,
            DisableSettingOption disableSettingOption) {
        super(Material.DIAMOND_CHESTPLATE);
        this.settingGUI = settingGUI;
        this.hideArmorSettingItemTitle = hideArmorSettingItemTitle;
        this.hideArmorSettingItemLore = hideArmorSettingItemLore;
        this.enableSettingOption = enableSettingOption;
        this.disableSettingOption = disableSettingOption;
    }

    @Override
    public ItemStack toItemStack(Player player, FreeFightSetting setting) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, hideArmorSettingItemTitle.getMessage(player));
        List<Component> lore = hideArmorSettingItemLore.getLoreMessage(player);
        lore.add(Component.empty());
        lore.add(Component.text(
                setting.isHideArmor()
                        ? enableSettingOption.getMessage(player)
                        : disableSettingOption.getMessage(player)));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onClickInInventory(InventoryClickEvent e, FreeFightSetting settings) {
        Player player = (Player) e.getWhoClicked();

        settings.setHideArmor(!settings.isHideArmor());

        player.closeInventory();

        settingGUI.openInventory(player);
    }
}
