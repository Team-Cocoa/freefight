package kr.teamcocoa.freefight.translation.items;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class ChallengerTitle extends BaseMessage {

    private ChallengerTitle() {
        super(Items.CHALLENGER);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }
}
