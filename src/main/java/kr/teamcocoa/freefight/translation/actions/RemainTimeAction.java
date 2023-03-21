package kr.teamcocoa.freefight.translation.actions;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.utils.StringUtils;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

@Getter
public class RemainTimeAction extends BaseMessage {

    private int sec;

    public RemainTimeAction(int sec) {
        super(Actions.REMAIN_TIME);
        this.sec = sec;
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        String formattedMessage = MessageFormat.format(message, StringUtils.getTimeFormat(sec));
        return formattedMessage;
    }
}
