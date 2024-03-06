package kr.teamcocoa.freefight.translation.items.settings;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.items.Items;
import org.bukkit.entity.Player;

public class HideArmorSettingItemTitle extends BaseMessage {

    private static HideArmorSettingItemTitle instance;

    public static HideArmorSettingItemTitle getInstance() {
        if(instance == null) {
            instance = new HideArmorSettingItemTitle();
        }
        return instance;
    }

    private HideArmorSettingItemTitle() {
        super(Items.SETTING_HIDE_ARMOR);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
