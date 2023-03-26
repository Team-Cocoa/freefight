package kr.teamcocoa.freefight.translation.lores;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.LoreMessage;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@Getter
public class PotLeftLore extends BaseMessage implements LoreMessage {

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

    @Override
    public List<Component> getLoreMessage(Player player) {
        return Arrays.asList(Component.text(getMessage(player)));
    }
}
