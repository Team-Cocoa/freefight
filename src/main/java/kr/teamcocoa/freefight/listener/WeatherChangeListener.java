package kr.teamcocoa.freefight.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void onChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

}
