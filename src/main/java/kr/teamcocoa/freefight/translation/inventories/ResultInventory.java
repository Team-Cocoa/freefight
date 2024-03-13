package kr.teamcocoa.freefight.translation.inventories;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class ResultInventory extends BaseMessage {

    private ResultInventory() {
        super(Inventories.RESULT);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
