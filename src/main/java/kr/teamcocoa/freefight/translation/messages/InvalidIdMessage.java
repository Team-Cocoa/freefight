package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class InvalidIdMessage extends BaseMessage {

    private static InvalidIdMessage instance;

    public static InvalidIdMessage getInstance() {
        if(instance == null) {
            instance = new InvalidIdMessage();
        }
        return instance;
    }

    private InvalidIdMessage() {
        super(Messages.INVALID_MATCH_ID);
    }

    @Override
    public String getMessage(Player player) {
        String message = FreeFight.getPrefix() + getRawMessage(player.getUniqueId());
        return message;
    }
}
