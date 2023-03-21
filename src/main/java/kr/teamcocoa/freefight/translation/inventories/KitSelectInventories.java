package kr.teamcocoa.freefight.translation.inventories;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class KitSelectInventories extends BaseMessage {

    private static KitSelectInventories instance;

    public static KitSelectInventories getInstance() {
        if(instance == null) {
            instance = new KitSelectInventories();
        }
        return instance;
    }

    private KitSelectInventories() {
        super(Inventories.KIT_SELECT);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }

}
