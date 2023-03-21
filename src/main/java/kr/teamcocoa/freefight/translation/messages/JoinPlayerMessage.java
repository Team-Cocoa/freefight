package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class JoinPlayerMessage extends BaseMessage {

    private String joinedPlayer;

    public JoinPlayerMessage(Player player) {
        super(Messages.JOIN_PLAYER);
        this.joinedPlayer = player.getName();
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        String formattedMessage = MessageFormat.format(message, joinedPlayer);
        return FreeFight.getPrefix() + formattedMessage;
    }
}
