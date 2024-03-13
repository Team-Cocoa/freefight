package kr.teamcocoa.freefight.translation.messages;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class SessionErrorMessage extends BaseMessage {

    private SessionErrorMessage() {
        super(Messages.SESSION_ERROR);
    }

    @Override
    public String getMessage(Player player) {
        return FreeFight.getPrefix() + getRawMessage(player.getUniqueId());
    }
}
