package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class MatchInfoButtonMessage extends BaseMessage {
    private static MatchInfoButtonMessage instance;

    public static MatchInfoButtonMessage getInstance() {
        if(instance == null) {
            instance = new MatchInfoButtonMessage();
        }
        return instance;
    }

    private MatchInfoButtonMessage() {
        super(Messages.MATCH_INFO_BUTTON);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
