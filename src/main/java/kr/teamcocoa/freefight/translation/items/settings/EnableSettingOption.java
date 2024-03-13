package kr.teamcocoa.freefight.translation.items.settings;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.items.Items;
import org.bukkit.entity.Player;

@Singleton
public class EnableSettingOption extends BaseMessage {

    private EnableSettingOption() {
        super(Items.SETTING_ENABLE);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
