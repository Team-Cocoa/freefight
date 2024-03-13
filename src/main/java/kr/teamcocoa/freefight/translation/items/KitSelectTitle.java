package kr.teamcocoa.freefight.translation.items;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class KitSelectTitle extends BaseMessage {

    private KitSelectTitle() {
        super(Items.KIT_SELECT);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }
}
