package kr.teamcocoa.freefight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static boolean componentEquals(Component component, String string) {
        if(component instanceof TextComponent textComponent) {
            return textComponent.content().equals(string);
        }
        return false;
    }

    public static boolean componentEquals(Component first, Component second) {
        if(first instanceof TextComponent textComponentFirst && second instanceof TextComponent textComponentSecond) {
            return componentEquals(textComponentFirst, textComponentSecond.content());
        }
        return false;
    }

}
