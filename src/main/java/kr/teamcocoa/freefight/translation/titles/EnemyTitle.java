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
        String message = LanguageController.getMessage(player.getUniqueId(), TypeEnum.FREEFIGHT, getTranslatable().getNode());
        String formattedMessage = MessageFormat.format(message, enemy);
        return formattedMessage;
    }

}
