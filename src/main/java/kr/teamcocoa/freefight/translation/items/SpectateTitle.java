package kr.teamcocoa.freefight.translation.items;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class SpectateTitle extends BaseMessage {

    private static SpectateTitle instance;

    public static SpectateTitle getInstance() {
        if(instance == null) {
            instance = new SpectateTitle();
        }
        return instance;
    }

    private SpectateTitle() {
        super(Items.SPECTATE);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }
}
