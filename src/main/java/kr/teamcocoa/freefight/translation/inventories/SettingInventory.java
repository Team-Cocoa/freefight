package kr.teamcocoa.freefight.translation.inventories;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class SettingInventory extends BaseMessage {

    private static SettingInventory instance;

    public static SettingInventory getInstance() {
        if(instance == null) {
            instance = new SettingInventory();
        }
        return instance;
    }

    private SettingInventory() {
        super(Inventories.SETTING);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
