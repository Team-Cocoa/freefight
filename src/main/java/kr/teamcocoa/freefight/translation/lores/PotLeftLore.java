package kr.teamcocoa.freefight.translation.lores;

import kr.teamcocoa.freefight.translation.BaseMessage;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

@Getter
public class PotLeftLore extends BaseMessage {

    private int potLeft;

    public PotLeftLore(int potLeft) {
        super(Lores.POT_LEFT);
        this.potLeft = potLeft;
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return MessageFormat.format(message, potLeft);
    }
}
