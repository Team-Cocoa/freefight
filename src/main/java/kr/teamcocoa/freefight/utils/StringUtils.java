package kr.teamcocoa.freefight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static boolean componentEquals(Component component, String string) {
        if(component instanceof TextComponent textComponent) {
            return textComponent.content().equals(StringUtils.color(string));
        }
        return false;
    }

    public static boolean componentEquals(Component first, Component second) {
        if(first instanceof TextComponent textComponentFirst && second instanceof TextComponent textComponentSecond) {
            return componentEquals(textComponentFirst, textComponentSecond.content());
        }
        return false;
    }

    public static String getTimeFormat(int sec) {
        String minute = "0" + sec / 60;
        String second = "0" + sec % 60;
        return minute.substring(minute.length() - 2) + ":" + second.substring(second.length() - 2);
    }

    public static String getTimestampToDate(long time){
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        String formattedDate = sdf.format(date);
        return formattedDate + " (UTC +9)";
    }

}
