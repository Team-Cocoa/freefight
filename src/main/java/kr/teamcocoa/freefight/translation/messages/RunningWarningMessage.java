package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

public class RunningWarningMessage extends BaseMessage {

    private static RunningWarningMessage instance;

    public static RunningWarningMessage getInstance() {
        if(instance == null) {
            instance = new RunningWarningMessage();
        }
        return instance;
    }

    private RunningWarningMessage() {
        super(Messages.RUNNING_WARNING);
    }

    @Override
    public String getMessage(Player player) {
        StringBuilder sb = new StringBuilder();
        for (String string : getArrayMessage(player.getUniqueId())) {
            sb.append(string + "\n");
        }
        return sb.toString().trim();
    }
}
