package kr.teamcocoa.freefight.translation.titles;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

@Getter
public class EnemyTitle extends BaseMessage {

    private String enemy;

    public EnemyTitle(String enemy) {
        super(Titles.ENEMY);
        this.enemy = enemy;
    }

    @Override
    public String getMessage(Player player) {
        String message = getRawMessage(player.getUniqueId());
        String formattedMessage = MessageFormat.format(message, enemy);
        return formattedMessage;
    }

}
