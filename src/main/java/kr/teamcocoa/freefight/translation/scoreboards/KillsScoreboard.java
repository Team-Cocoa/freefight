package kr.teamcocoa.freefight.translation.scoreboards;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class KillsScoreboard extends BaseMessage {

    private KillsScoreboard() {
        super(Scoreboards.KILLS);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
