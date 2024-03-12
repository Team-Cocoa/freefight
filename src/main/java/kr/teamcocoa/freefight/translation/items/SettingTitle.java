package kr.teamcocoa.freefight.translation.items;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class SettingTitle extends BaseMessage {

    private static SettingTitle instance;

    public static SettingTitle getInstance() {
        if(instance == null) {
            instance = new SettingTitle();
        }
        return instance;
    }

    private SettingTitle() {
        super(Items.SETTING);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
