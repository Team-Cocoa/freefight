package kr.teamcocoa.freefight.main;

import kr.teamcocoa.freefight.listener.*;
import kr.teamcocoa.freefight.mysql.MySQL;
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
        MySQL.connect();
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
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
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new EntityRegainHealthListener(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(), this);
    }
}
