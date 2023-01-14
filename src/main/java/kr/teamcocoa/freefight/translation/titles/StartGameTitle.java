package kr.teamcocoa.freefight.translation.titles;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class StartGameTitle extends BaseMessage {

    private static StartGameTitle instance;

    public static StartGameTitle getInstance() {
        if(instance == null) {
            instance = new StartGameTitle();
        }
        return instance;
    }

    private StartGameTitle() {
        super(Titles.START_GAME);
    }

    @Override
    public String getMessage(Player player) {
        String message = LanguageController.getMessage(player.getUniqueId(), TypeEnum.FREEFIGHT, getTranslatable().getNode());
        return message;
    }
}
