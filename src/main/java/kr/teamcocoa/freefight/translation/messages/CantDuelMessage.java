package kr.teamcocoa.freefight.translation.messages;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class CantDuelMessage extends BaseMessage {

    private CantDuelMessage() {
        super(Messages.CANT_DUEL_WHILE_SPEC);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return FreeFight.getPrefix() + message;
    }
}
