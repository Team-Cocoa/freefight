package kr.teamcocoa.freefight.translation.scoreboards;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class KillStreakScoreboard extends BaseMessage {

    private static KillStreakScoreboard instance;

    public static KillStreakScoreboard getInstance() {
        if(instance == null) {
            instance = new KillStreakScoreboard();
        }
        return instance;
    }

    private KillStreakScoreboard() {
        super(Scoreboards.KILL_STREAK);
    }

    @Override
    public String getMessage(Player player) {
        String message = LanguageController.getMessage(player.getUniqueId(), TypeEnum.FREEFIGHT, getTranslatable().getNode());
        return message;
    }

}
