package kr.teamcocoa.freefight.translation.titles;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class DrawTitle extends BaseMessage {

    private DrawTitle() {
        super(Titles.DRAW);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }
}
