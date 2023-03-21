package kr.teamcocoa.freefight.translation.items;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class KitSelectTitle extends BaseMessage {

    private static KitSelectTitle instance;

    public static KitSelectTitle getInstance() {
        if(instance == null) {
            instance = new KitSelectTitle();
        }
        return instance;
    }

    private KitSelectTitle() {
        super(Items.KIT_SELECT);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }
}
