package kr.teamcocoa.freefight.messages;

import kr.teamcocoa.freefight.translation.Messages;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class KillLogMessage extends AbstractMessage {

    public KillLogMessage(int argumentLength) {
        super(Messages.KILL_LOG, new Arguments(argumentLength));
    }

    @Override
    public void sendMessage(Player player) {
        MessageFormat.format("", getArguments().getArguments());
    }
}
