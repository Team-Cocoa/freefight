package kr.teamcocoa.freefight.translation.titles;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class VictoryTitle extends BaseMessage {

    private static VictoryTitle instance;

    public static VictoryTitle getInstance() {
        if(instance == null) {
            instance = new VictoryTitle();
        }
        return instance;
    }

    private VictoryTitle() {
        super(Titles.VICTORY);
    }

    @Override
    public String getMessage(Player player) {
        String message = LanguageController.getMessage(player.getUniqueId(), TypeEnum.FREEFIGHT, getTranslatable().getNode());
        return message;
    }
}
