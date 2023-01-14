package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

@Getter
public class KitChangeMessage extends BaseMessage {

    private String changedKit;

    public KitChangeMessage(Kits kits) {
        super(Messages.KIT_CHANGE);
        this.changedKit = Kits.getNameByEnum(kits);
    }

    @Override
    public String getMessage(Player player) {
        String message = LanguageController.getMessage(player.getUniqueId(), TypeEnum.FREEFIGHT, getTranslatable().getNode());
        String formattedMessage = MessageFormat.format(message, changedKit);
        return FreeFight.getPrefix() + formattedMessage;
    }
}
