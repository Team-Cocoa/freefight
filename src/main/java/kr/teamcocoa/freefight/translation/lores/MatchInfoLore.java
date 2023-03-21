package kr.teamcocoa.freefight.translation.lores;

import kr.teamcocoa.freefight.translation.BaseMessage;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.MessageFormat;

@Getter
public class MatchInfoLore extends BaseMessage {

    private static final DecimalFormat format = new DecimalFormat("#.##");

    private String playerName;
    private String health;
    private String hunger;
    private String saturation;
    private String damageInComing;
    private String damageOutComing;

    private Object[] arguments;

    public MatchInfoLore(String playerName, double health, double hunger, double saturation, double damageInComing, double damageOutComing) {
        super(Lores.MATCH_INFO);
        this.playerName = playerName;
        this.health = format.format(health);
        this.hunger = format.format(hunger);
        this.saturation = format.format(saturation);
        this.damageInComing = format.format(damageInComing);
        this.damageOutComing = format.format(damageOutComing);

        this.arguments = new Object[] { playerName, health, hunger, saturation, damageInComing, damageOutComing };
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return MessageFormat.format(message, arguments);
    }
}
