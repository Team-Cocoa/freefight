package kr.teamcocoa.freefight.translation.lores;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class NoWinnerLore extends BaseMessage {

    private static NoWinnerLore instance;

    public static NoWinnerLore getInstance() {
        if(instance == null) {
            instance = new NoWinnerLore();
        }
        return instance;
    }

    private NoWinnerLore() {
        super(Lores.NO_WINNER);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
