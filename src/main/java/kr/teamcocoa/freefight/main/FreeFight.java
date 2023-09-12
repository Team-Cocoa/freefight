package kr.teamcocoa.freefight.main;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import eu.cloudnetservice.driver.event.EventManager;
import eu.cloudnetservice.driver.permission.PermissionManagement;
import eu.cloudnetservice.ext.platforminject.api.stereotype.Dependency;
import eu.cloudnetservice.ext.platforminject.api.stereotype.PlatformPlugin;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.settings.PacketEventsSettings;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import kr.teamcocoa.freefight.commands.*;
import kr.teamcocoa.freefight.listener.bukkit.*;
import kr.teamcocoa.freefight.listener.packet.*;
import kr.teamcocoa.freefight.mysql.FreeFightDatabase;
import kr.teamcocoa.freefight.tab.TabListener;
import kr.teamcocoa.freefight.utils.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;


@Singleton
@PlatformPlugin(
        platform = "bukkit",
        name = "FreeFight",
        version = "1.0",
        authors = "fixca",
        dependencies = @Dependency(name = "CloudNet-CloudPerms")
)
public class FreeFight extends JavaPlugin {

    @Getter
    private static EventManager eventManager;

    @Getter
    private static PermissionManagement permissionManagement;

    @Inject
    public FreeFight(
            @NonNull EventManager eventManager,
            @NonNull PermissionManagement permissionManagement
    ) {
        FreeFight.eventManager = eventManager;
        FreeFight.permissionManagement = permissionManagement;
    }


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
        initPacketEvents();
        FreeFightDatabase.init();
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        PacketEvents.get().terminate();
    }

    private void initPacketEvents() {
        PacketEvents.create(this);
        PacketEventsSettings settings = PacketEvents.get().getSettings();
        settings
                .fallbackServerVersion(ServerVersion.v_1_18_2)
                .checkForUpdates(true)
                .bStats(true);
        PacketEvents.get().load();
    }

    private void init() {
        loadCommands();
        loadListeners();
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (World world : Bukkit.getWorlds()) {
                world.setTime(0);
            }
        },0L, 1L);
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, false);
        }
    }

    private void loadCommands() {
        getCommand("forcetp").setExecutor(new ForceTPCommand());
        getCommand("checkmatch").setExecutor(new CheckMatchCommand());

        eventManager.unregisterListeners(this.getClass().getClassLoader());
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
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);

        TabListener tabListener = new TabListener();
        getServer().getPluginManager().registerEvents(tabListener, this);

        eventManager.registerListener(tabListener);

        // Packet Listeners
        PacketEvents.get().registerListener(new ParticleListener());
//        PacketEvents.get().registerListener(new SweepListener());

        PacketEvents.get().init();
    }
}
