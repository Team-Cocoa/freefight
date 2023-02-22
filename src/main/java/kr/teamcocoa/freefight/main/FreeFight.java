package kr.teamcocoa.freefight.main;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.wrapper.Wrapper;
import kr.teamcocoa.freefight.commands.ForceTPCommand;
import kr.teamcocoa.freefight.listener.*;
import kr.teamcocoa.freefight.mysql.FreeFightDatabase;
import kr.teamcocoa.freefight.tab.TabListener;
import kr.teamcocoa.freefight.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FreeFight extends JavaPlugin {

    @Getter
    private static FreeFight instance;

    @Getter
    private static final String prefix = StringUtils.color("&a[&dFreeFight&a] &r");

    @Getter
    @Setter
    private static boolean forceTPMode = false;

    @Override
    public void onLoad() {
        instance = this;
        FreeFightDatabase.init();
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
        Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getWorld("TestFreeFight").setTime(0),0L, 1L);
    }

    private void loadCommands() {
        getCommand("forcetp").setExecutor(new ForceTPCommand());

        CloudNetDriver.getInstance().getEventManager().unregisterListeners(this.getClass().getClassLoader());
        Wrapper.getInstance().unregisterPacketListenersByClassLoader(this.getClass().getClassLoader());
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new EntityRegainHealthListener(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerPickupArrowListener(), this);
        getServer().getPluginManager().registerEvents(new PotionSplashListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileLaunchListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChangeLanguageListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new VulcanListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(), this);

        TabListener tabListener = new TabListener();
        getServer().getPluginManager().registerEvents(tabListener, this);

        CloudNetDriver.getInstance().getEventManager().registerListener(tabListener);
    }
}
