package kr.teamcocoa.freefight.translation.messages;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class MatchInfoButtonMessage extends BaseMessage {

    private MatchInfoButtonMessage() {
        super(Messages.MATCH_INFO_BUTTON);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
