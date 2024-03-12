package kr.teamcocoa.freefight.translation.lores.settings;

import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.LoreMessage;
import kr.teamcocoa.freefight.translation.lores.Lores;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class HideArmorSettingItemLore extends BaseMessage implements LoreMessage {

    private static HideArmorSettingItemLore instance;

    public static HideArmorSettingItemLore getInstance() {
        if(instance == null) {
            instance = new HideArmorSettingItemLore();
        }
        return instance;
    }

    private HideArmorSettingItemLore() {
        super(Lores.SETTING_HIDE_ARMOR);
    }

    @Override
    public List<Component> getLoreMessage(Player player) {
        String[] rawMessages = getMessage(player).split("\n");
        List<Component> list = new LinkedList<>();
        for (String rawMessage : rawMessages) {
            list.add(Component.text(rawMessage));
        }
        return list;
    }

    @Override
    public String getMessage(Player player) {
        StringBuilder sb = new StringBuilder();

        String[] messages = getArrayMessage(player.getUniqueId());
        for (int i = 0; i < messages.length; i++) {
            String message = messages[i].trim();
            sb.append(message + "\n");
        }

        return sb.toString();
    }
}
