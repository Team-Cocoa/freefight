package kr.teamcocoa.freefight.translation.items;

import kr.teamcocoa.freefight.translation.BaseMessage;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

@Getter
public class PlayerHeadTitle extends BaseMessage {

    private String name;

    public PlayerHeadTitle(String name) {
        super(Items.PLAYER_HEAD);
        this.name = name;
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return MessageFormat.format(message, name);
    }
}
