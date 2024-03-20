package kr.teamcocoa.freefight.translation.items.settings;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.items.Items;
import org.bukkit.entity.Player;

public class DisplaySessionPlayersItemTitle extends BaseMessage {

    private static DisplaySessionPlayersItemTitle instance;

    public static DisplaySessionPlayersItemTitle getInstance() {
        if(instance == null) {
            instance = new DisplaySessionPlayersItemTitle();
        }
        return instance;
    }

    public DisplaySessionPlayersItemTitle() {
        super(Items.SETTING_DISPLAY_SESSION_PLAYERS);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
    
}
