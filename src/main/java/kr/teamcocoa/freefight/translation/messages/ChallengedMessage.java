package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class ChallengedMessage extends BaseMessage {

    private String playerName;

    public ChallengedMessage(Player player) {
        super(Messages.CHALLENGED);
        this.playerName = player.getName();
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        String formattedMessage = MessageFormat.format(message, playerName);
        return FreeFight.getPrefix() + formattedMessage;
    }
}
