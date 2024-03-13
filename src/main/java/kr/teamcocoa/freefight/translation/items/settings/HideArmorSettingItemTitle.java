package kr.teamcocoa.freefight.translation.items.settings;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.items.Items;
import org.bukkit.entity.Player;

@Singleton
public class HideArmorSettingItemTitle extends BaseMessage {

    private HideArmorSettingItemTitle() {
        super(Items.SETTING_HIDE_ARMOR);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
