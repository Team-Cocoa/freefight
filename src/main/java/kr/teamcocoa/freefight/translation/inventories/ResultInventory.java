package kr.teamcocoa.freefight.translation.inventories;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class ResultInventory extends BaseMessage {

    private static ResultInventory instance;

    public static ResultInventory getInstance() {
        if(instance == null) {
            instance = new ResultInventory();
        }
        return instance;
    }

    private ResultInventory() {
        super(Inventories.RESULT);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }
}
