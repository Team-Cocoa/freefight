package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class ChallengeMessage extends BaseMessage {

    private String playerName;

    public ChallengeMessage(Player player) {
        super(Messages.CHALLENGE);
        this.playerName = player.getName();
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        String formattedMessage = MessageFormat.format(message, playerName);
        return FreeFight.getPrefix() + formattedMessage;
    }
}
