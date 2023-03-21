package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class LeavePlayerMessage extends BaseMessage {

    private String leftPlayer;

    public LeavePlayerMessage(Player player) {
        super(Messages.LEAVE_PLAYER);
        this.leftPlayer = player.getName();
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        String formattedMessage = MessageFormat.format(message, leftPlayer);
        return FreeFight.getPrefix() + formattedMessage;
    }
}
