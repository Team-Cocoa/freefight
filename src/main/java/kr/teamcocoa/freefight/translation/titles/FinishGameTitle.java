package kr.teamcocoa.freefight.translation.titles;

import kr.teamcocoa.freefight.translation.BaseMessage;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

@Getter
public class FinishGameTitle extends BaseMessage {

    private String winner;

    private String leftHealth;
    private Object[] arguments;

    public FinishGameTitle(String winner, double leftHealth) {
        super(Titles.FINISH_GAME);
        this.winner = winner;
        this.leftHealth = String.format("%.2f", leftHealth);
        this.arguments = new Object[] { winner, leftHealth };
    }

    @Override
    public String getMessage(Player player) {
        return MessageFormat.format(getRawMessage(player.getUniqueId()), arguments);
    }
}
