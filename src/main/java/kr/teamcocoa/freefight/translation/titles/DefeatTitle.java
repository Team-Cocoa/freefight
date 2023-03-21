package kr.teamcocoa.freefight.translation.titles;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class DefeatTitle extends BaseMessage {

    private static DefeatTitle instance;

    public static DefeatTitle getInstance() {
        if(instance == null) {
            instance = new DefeatTitle();
        }
        return instance;
    }

    private DefeatTitle() {
        super(Titles.DEFEAT);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }
}
