package kr.teamcocoa.freefight.translation;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class BaseMessage {

    private Translatable translatable;

    public abstract String getMessage(Player player);

}
