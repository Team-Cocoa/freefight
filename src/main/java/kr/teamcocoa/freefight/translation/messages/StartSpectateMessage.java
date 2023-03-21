package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class StartSpectateMessage extends BaseMessage {

    private static StartSpectateMessage instance;

    public static StartSpectateMessage getInstance() {
        if(instance == null) {
            instance = new StartSpectateMessage();
        }
        return instance;
    }

    private StartSpectateMessage() {
        super(Messages.START_SPECTATE);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return FreeFight.getPrefix() + message;
    }
}
