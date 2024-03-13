package kr.teamcocoa.freefight.translation.titles;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.translation.BaseMessage;
import org.bukkit.entity.Player;

@Singleton
public class DrawGameTitle extends BaseMessage {

    private DrawGameTitle() {
        super(Titles.DRAW_GAME);
    }

    @Override
    public String getMessage(Player player) {
        return getRawMessage(player.getUniqueId());
    }

}
