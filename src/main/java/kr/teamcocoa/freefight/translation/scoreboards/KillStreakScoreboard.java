package kr.teamcocoa.freefight.translation.scoreboards;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class KillStreakScoreboard extends BaseMessage {

    private KillStreakScoreboard() {
        super(Scoreboards.KILL_STREAK);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
