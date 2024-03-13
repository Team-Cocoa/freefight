package kr.teamcocoa.freefight.translation.items;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class SpectateTitle extends BaseMessage {

    private SpectateTitle() {
        super(Items.SPECTATE);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
