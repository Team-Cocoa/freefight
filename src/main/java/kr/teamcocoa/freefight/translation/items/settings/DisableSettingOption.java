package kr.teamcocoa.freefight.translation.items.settings;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.items.Items;
import org.bukkit.entity.Player;

public class DisableSettingOption extends BaseMessage {

    private static DisableSettingOption instance;

    public static DisableSettingOption getInstance() {
        if(instance == null) {
            instance = new DisableSettingOption();
        }
        return instance;
    }

    private DisableSettingOption() {
        super(Items.SETTING_DISABLE);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
