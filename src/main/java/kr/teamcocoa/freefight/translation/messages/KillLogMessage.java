package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.BaseMessage;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

@Getter
public class KillLogMessage extends BaseMessage {

    private String killer;
    private String victim;
    private String leftHealth;
    private Kits kits;

    private Object[] arguments;

    public KillLogMessage(String killer, String victim, double leftHealth, Kits kits) {
        super(Messages.KILL_LOG);
        this.killer = killer;
        this.victim = victim;
        this.leftHealth = String.format("%.2f", leftHealth);
        this.kits = kits;
        this.arguments = new Object[] { killer, victim, leftHealth, Kits.getNameByEnum(kits) };
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        String formattedMessage = MessageFormat.format(message, arguments);
        return FreeFight.getPrefix() + formattedMessage;
    }
}
