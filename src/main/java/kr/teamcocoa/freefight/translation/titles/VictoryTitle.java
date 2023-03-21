package kr.teamcocoa.freefight.translation.titles;

import kr.teamcocoa.freefight.translation.BaseMessage;
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
        String message = getRawMessage(player.getUniqueId());
        return message;
    }
}
