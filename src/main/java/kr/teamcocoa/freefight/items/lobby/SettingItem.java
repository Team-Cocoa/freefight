package kr.teamcocoa.freefight.items.lobby;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.core.bukkit.utils.ComponentUtils;
import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.gui.KitSelectGUI;
import kr.teamcocoa.freefight.gui.SettingGUI;
import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.items.ClickAble;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.translation.items.SettingTitle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@Singleton
public class SettingItem extends AbstractItem implements ClickAble {

    private SettingGUI settingGUI;
    private SettingTitle settingTitle;

    @Inject
    private SettingItem(
            SettingGUI settingGUI,
            SettingTitle settingTitle) {
        super(Material.COMPARATOR);
        this.settingGUI = settingGUI;
        this.settingTitle = settingTitle;
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
                settingTitle.getMessage(player))) {
            settingGUI.openInventory(player);
        }
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, settingTitle.getMessage(player));

        return itemStack;
    }
}
