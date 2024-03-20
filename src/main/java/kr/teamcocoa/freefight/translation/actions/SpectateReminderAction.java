package kr.teamcocoa.freefight.translation.actions;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class SpectateReminderAction extends BaseMessage {

    private static SpectateReminderAction instance;

    public static SpectateReminderAction getInstance() {
        if(instance == null) {
            instance = new SpectateReminderAction();
        }
        return instance;
    }

    private SpectateReminderAction() {
        super(Actions.SPECTATING);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
