package kr.teamcocoa.freefight.translation.scoreboards;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class KillsScoreboard extends BaseMessage {

    private static KillsScoreboard instance;

    public static KillsScoreboard getInstance() {
        if(instance == null) {
            instance = new KillsScoreboard();
        }
        return instance;
    }

    private KillsScoreboard() {
        super(Scoreboards.KILLS);
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return message;
    }

}
