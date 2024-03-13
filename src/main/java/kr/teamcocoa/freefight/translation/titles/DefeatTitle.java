package kr.teamcocoa.freefight.translation.titles;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class DefeatTitle extends BaseMessage {

    private DefeatTitle() {
        super(Titles.DEFEAT);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
