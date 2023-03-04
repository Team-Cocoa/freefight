package kr.teamcocoa.freefight.translation.titles;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class DrawGameTitle extends BaseMessage {

    private static DrawGameTitle instance;

    public static DrawGameTitle getInstance() {
        if(instance == null) {
            instance = new DrawGameTitle();
        }
        return instance;
    }

    private DrawGameTitle() {
        super(Titles.DRAW_GAME);
    }

    @Override
    public String getMessage(Player player) {
        String message = LanguageController.getMessage(player.getUniqueId(), TypeEnum.FREEFIGHT, getTranslatable().getNode());
        return message;
    }

}
