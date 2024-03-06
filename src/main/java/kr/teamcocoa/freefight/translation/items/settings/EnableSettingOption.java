package kr.teamcocoa.freefight.translation.items.settings;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.items.Items;
import org.bukkit.entity.Player;

public class EnableSettingOption extends BaseMessage {

    private static EnableSettingOption instance;

    public static EnableSettingOption getInstance() {
        if(instance == null) {
            instance = new EnableSettingOption();
        }
        return instance;
    }

    private EnableSettingOption() {
        super(Items.SETTING_ENABLE);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
