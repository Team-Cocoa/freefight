package kr.teamcocoa.freefight.main;

import kr.teamcocoa.freefight.listener.PlayerDeathListener;
import kr.teamcocoa.freefight.listener.PlayerJoinQuitListener;
import kr.teamcocoa.freefight.utils.StringUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class FreeFight extends JavaPlugin {

    @Getter
    private static FreeFight instance;

    @Getter
    private static String prefix = StringUtils.color("&a[&dFreeFight&a] &r");

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

    }

    private void init() {
        loadCommands();
        loadListeners();
    }

    private void loadCommands() {

    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }
}
