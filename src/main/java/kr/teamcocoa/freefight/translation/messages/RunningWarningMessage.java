package kr.teamcocoa.freefight.translation.messages;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.core.utils.StringUtils;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class RunningWarningMessage extends BaseMessage {

    private RunningWarningMessage() {
        super(Messages.RUNNING_WARNING);
    }

    @Override
    public String getMessage(Player player) {
        StringBuilder sb = new StringBuilder();
        for (String string : getArrayMessage(player.getUniqueId())) {
            sb.append(string + "\n");
        }
        return
                StringUtils.color("&7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n\n") +
                sb.toString().trim() +
                StringUtils.color("\n\n&7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }
}
