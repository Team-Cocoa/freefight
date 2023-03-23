package kr.teamcocoa.freefight.translation;

import kr.teamcocoa.language.enums.TypeEnum;
import kr.teamcocoa.language.languages.LanguageController;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class BaseMessage {

    private Translatable translatable;

    public abstract String getMessage(Player player);

    protected String getRawMessage(UUID uuid) {
        return LanguageController.getMessage(uuid, TypeEnum.FREEFIGHT, getTranslatable().getNode());
    }

    protected String getArrayMessage(UUID uuid) {
        StringBuilder sb = new StringBuilder();
        String[] tokens = getRawMessage(uuid).replace("[", "").replace("]", "").split(",");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            sb.append(token + (i == tokens.length - 1 ? "" : "\n"));
        }
        return sb.toString();
    }

}
