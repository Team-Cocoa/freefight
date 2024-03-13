package kr.teamcocoa.freefight.translation.lores;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class NoWinnerLore extends BaseMessage {

    private NoWinnerLore() {
        super(Lores.NO_WINNER);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
