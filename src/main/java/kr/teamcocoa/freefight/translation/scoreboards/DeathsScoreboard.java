package kr.teamcocoa.freefight.translation.scoreboards;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import org.bukkit.entity.Player;

public class DeathsScoreboard extends BaseMessage {

    private static DeathsScoreboard instance;

    public static DeathsScoreboard getInstance() {
        if(instance == null) {
            instance = new DeathsScoreboard();
        }
        return instance;
    }

    private DeathsScoreboard() {
        super(Scoreboards.DEATHS);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }

}
