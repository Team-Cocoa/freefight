package kr.teamcocoa.freefight.translation.items;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class KitSelectTitle extends BaseMessage {

    private static KitSelectTitle instance;

    public static KitSelectTitle getInstance() {
        if(instance == null) {
            instance = new KitSelectTitle();
        }
        return instance;
    }

    private KitSelectTitle() {
        super(Items.KIT_SELECT);
    }

    @Override
    public String getMessage(Player player) {
        String message = LanguageController.getMessage(player.getUniqueId(), TypeEnum.FREEFIGHT, getTranslatable().getNode());
        return message;
    }
}
