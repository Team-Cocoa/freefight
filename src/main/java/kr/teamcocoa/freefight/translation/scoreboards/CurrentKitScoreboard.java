package kr.teamcocoa.freefight.translation.scoreboards;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class CurrentKitScoreboard extends BaseMessage {

    private CurrentKitScoreboard() {
        super(Scoreboards.CURRENT_KIT);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
