package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class DifferentKitMessage extends BaseMessage {

    private String kitName;

    public DifferentKitMessage(Kits kits) {
        super(Messages.DIFFERENT_KIT);
        this.kitName = Kits.getNameByEnum(kits);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        String formattedMessage = MessageFormat.format(message, kitName);
        return FreeFight.getPrefix() + formattedMessage;
    }
}
