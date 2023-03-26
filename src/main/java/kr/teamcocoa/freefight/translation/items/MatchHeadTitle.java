package kr.teamcocoa.freefight.translation.items;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class MatchHeadTitle extends BaseMessage {

    private int id;

    public MatchHeadTitle(int id) {
        super(Items.MATCH_HEAD);
        this.id = id;
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return MessageFormat.format(message, id);
    }
}
