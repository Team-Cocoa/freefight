package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class SessionErrorMessage extends BaseMessage {

    private static SessionErrorMessage instance;

    public static SessionErrorMessage getInstance() {
        if(instance == null) {
            instance = new SessionErrorMessage();
        }
        return instance;
    }

    private SessionErrorMessage() {
        super(Messages.SESSION_ERROR);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return FreeFight.getPrefix() + message;
    }
}
