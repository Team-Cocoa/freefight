package kr.teamcocoa.freefight.translation.items;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class SettingTitle extends BaseMessage {

    private SettingTitle() {
        super(Items.SETTING);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
