package kr.teamcocoa.freefight.translation.inventories;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class KitSelectInventories extends BaseMessage {

    private KitSelectInventories() {
        super(Inventories.KIT_SELECT);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
