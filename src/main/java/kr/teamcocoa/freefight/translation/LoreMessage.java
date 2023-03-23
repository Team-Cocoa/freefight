package kr.teamcocoa.freefight.translation;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public interface LoreMessage {

    List<Component> getLoreMessage(Player player);

}
