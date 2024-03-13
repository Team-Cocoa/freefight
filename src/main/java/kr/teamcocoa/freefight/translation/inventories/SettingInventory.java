package kr.teamcocoa.freefight.translation.inventories;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class SettingInventory extends BaseMessage {

    private SettingInventory() {
        super(Inventories.SETTING);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
