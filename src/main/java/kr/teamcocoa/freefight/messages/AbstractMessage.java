package kr.teamcocoa.freefight.messages;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.entity.Player;

public abstract class AbstractMessage {

    private Translatable messageNode;

    @Getter(AccessLevel.PROTECTED)
    private Arguments arguments;

    protected AbstractMessage(Translatable messageNode, Arguments arguments) {
        this.messageNode = messageNode;
        if(arguments == null) {
            this.arguments = Arguments.empty();
        }
        else {
            this.arguments = arguments;
        }
    }

    public abstract void sendMessage(Player player);

}
