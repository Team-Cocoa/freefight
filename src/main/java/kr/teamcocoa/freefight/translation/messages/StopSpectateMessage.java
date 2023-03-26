package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class StopSpectateMessage extends BaseMessage {

    private static StopSpectateMessage instance;

    public static StopSpectateMessage getInstance() {
        if(instance == null) {
            instance = new StopSpectateMessage();
        }
        return instance;
    }

    private StopSpectateMessage() {
        super(Messages.STOP_SPECTATE);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return FreeFight.getPrefix() + message;
    }
}
