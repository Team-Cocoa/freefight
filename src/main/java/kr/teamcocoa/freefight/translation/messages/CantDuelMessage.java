package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class CantDuelMessage extends BaseMessage {

    private static CantDuelMessage instance;

    public static CantDuelMessage getInstance() {
        if(instance == null) {
            instance = new CantDuelMessage();
        }
        return instance;
    }

    private CantDuelMessage() {
        super(Messages.CANT_DUEL_WHILE_SPEC);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return FreeFight.getPrefix() + message;
    }
}
