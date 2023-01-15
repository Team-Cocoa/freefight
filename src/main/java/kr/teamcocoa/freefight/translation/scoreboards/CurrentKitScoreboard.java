package kr.teamcocoa.freefight.translation.scoreboards;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class CurrentKitScoreboard extends BaseMessage {

    private static CurrentKitScoreboard instance;

    public static CurrentKitScoreboard getInstance() {
        if(instance == null) {
            instance = new CurrentKitScoreboard();
        }
        return instance;
    }

    private CurrentKitScoreboard() {
        super(Scoreboards.CURRENT_KIT);
    }

    @Override
    public String getMessage(Player player) {
        String message = LanguageController.getMessage(player.getUniqueId(), TypeEnum.FREEFIGHT, getTranslatable().getNode());
        return message;
    }
}
