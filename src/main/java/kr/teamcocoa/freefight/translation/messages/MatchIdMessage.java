package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.translation.BaseMessage;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.text.MessageFormat;


@Getter
public class MatchIdMessage extends BaseMessage {

    private int id;

    public MatchIdMessage(int id) {
        super(Messages.MATCH_ID);
        this.id = id;
    }

    @Override
    public String getMessage(Player player) {
        return MessageFormat.format(getRawMessage(player.getUniqueId()), id);
    }
}
