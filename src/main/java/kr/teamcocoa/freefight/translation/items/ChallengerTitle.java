package kr.teamcocoa.freefight.translation.items;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class ChallengerTitle extends BaseMessage {

    private static ChallengerTitle instance;

    public static ChallengerTitle getInstance() {
        if(instance == null) {
            instance = new ChallengerTitle();
        }
        return instance;
    }

    private ChallengerTitle() {
        super(Items.CHALLENGER);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }
}
