package kr.teamcocoa.freefight.translation.lores;

import kr.teamcocoa.freefight.session.result.ResultPlayer;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.LoreMessage;
import kr.teamcocoa.freefight.utils.HeadUtils;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

@Getter
public class MatchInfoLore extends BaseMessage implements LoreMessage {

    private static final DecimalFormat format = new DecimalFormat("#.##");

    private String playerName;
    private String health;
    private String hunger;
    private String saturation;
    private String damageInComing;
    private String damageOutComing;

    private Object[] arguments;

    public MatchInfoLore(ResultPlayer resultPlayer) {
        super(Lores.MATCH_INFO);
        this.playerName = HeadUtils.getNameCache().get(resultPlayer.getUuid());
        this.health = format.format(resultPlayer.getHealth());
        this.hunger = format.format(resultPlayer.getHunger());
        this.saturation = format.format(resultPlayer.getSaturation());
        this.damageInComing = format.format(resultPlayer.getDamageInComing());
        this.damageOutComing = format.format(resultPlayer.getDamageOutComing());

        this.arguments = new Object[] { playerName, health, hunger, saturation, damageInComing, damageOutComing };
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        return MessageFormat.format(message, arguments);
    }

    @Override
    public List<Component> getLoreMessage(Player player) {
        String[] rawMessages = getMessage(player).split("\n");
        List<Component> list = new LinkedList<>();
        for (String rawMessage : rawMessages) {
            list.add(Component.text(rawMessage));
        }
        return list;
    }
}
