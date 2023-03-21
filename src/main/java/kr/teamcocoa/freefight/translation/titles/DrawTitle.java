package kr.teamcocoa.freefight.translation.titles;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class DrawTitle extends BaseMessage {

    private static DrawTitle instance;

    public static DrawTitle getInstance() {
        if(instance == null) {
            instance = new DrawTitle();
        }
        return instance;
    }

    private DrawTitle() {
        super(Titles.DRAW);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }
}
