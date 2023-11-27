package kr.teamcocoa.freefight.main;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import eu.cloudnetservice.driver.event.EventManager;
import eu.cloudnetservice.driver.permission.PermissionManagement;
import eu.cloudnetservice.ext.platforminject.api.PlatformEntrypoint;
import eu.cloudnetservice.ext.platforminject.api.stereotype.Command;
import eu.cloudnetservice.ext.platforminject.api.stereotype.Dependency;
import eu.cloudnetservice.ext.platforminject.api.stereotype.PlatformPlugin;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.settings.PacketEventsSettings;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import kr.teamcocoa.core.utils.StringUtils;
import kr.teamcocoa.freefight.commands.CheckMatchCommand;
import kr.teamcocoa.freefight.commands.ForceTPCommand;
import kr.teamcocoa.freefight.listener.bukkit.*;
import kr.teamcocoa.freefight.listener.packet.ParticleListener;
import kr.teamcocoa.freefight.listener.packet.SweepListener;
import kr.teamcocoa.freefight.mysql.FreeFightDatabase;
import kr.teamcocoa.freefight.mysql.SessionDatabase;
import kr.teamcocoa.freefight.tab.TabListener;
import kr.teamcocoa.freefight.task.AfkCheckTask;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


@Singleton
@PlatformPlugin(
        platform = "bukkit",
        name = "FreeFight",
        version = "1.0",
        authors = "fixca",
        dependencies = {
                @Dependency(name = "CloudNet-CloudPerms"),
                @Dependency(name = "MySQL"),
                @Dependency(name = "BukkitRetrofit")
        },
        pluginFileNames = "plugin.yml",
        commands = {
                @Command(name = "forcetp"),
                @Command(name = "checkmatch")
        },
        api = "1.13"
)
public class FreeFight implements PlatformEntrypoint {

    @Getter
    private static EventManager eventManager;

    @Getter
    private static PermissionManagement permissionManagement;

    private PluginManager pluginManager;

    @Inject
    public FreeFight(
            @NonNull JavaPlugin plugin,
            @NonNull PluginManager pluginManager,
            @NonNull EventManager eventManager,
            @NonNull PermissionManagement permissionManagement
    ) {
        FreeFight.eventManager = eventManager;
        FreeFight.permissionManagement = permissionManagement;
        FreeFight.instance = plugin;
        this.pluginManager = pluginManager;
        initPacketEvents();
        FreeFightDatabase.init();
        SessionDatabase.registerConnectionPool();
        init();
    }

    @Getter
    private static JavaPlugin instance;

    @Getter
    private static final String prefix = StringUtils.color("&a[&dFreeFight&a] &r");

    @Getter
    @Setter
    private static boolean forceTPMode = false;

    @Override
    public void onDisable() {
        PacketEvents.get().terminate();
    }

    private void initPacketEvents() {
        PacketEvents.create(instance);
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
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            for (World world : Bukkit.getWorlds()) {
                world.setTime(0);
            }
        },0L, 1L);
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, false);
        }
        new AfkCheckTask().runTaskTimer(instance, 0L, 100L);
    }

    private void loadCommands() {
        instance.getCommand("forcetp").setExecutor(new ForceTPCommand());
        instance.getCommand("checkmatch").setExecutor(new CheckMatchCommand());

        eventManager.unregisterListeners(this.getClass().getClassLoader());
    }

    private void loadListeners() {
        pluginManager.registerEvents(new PlayerJoinQuitListener(), instance);
        pluginManager.registerEvents(new PlayerDeathListener(), instance);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), instance);
        pluginManager.registerEvents(new EntityRegainHealthListener(), instance);
        pluginManager.registerEvents(new FoodLevelChangeListener(), instance);
        pluginManager.registerEvents(new BlockBreakListener(), instance);
        pluginManager.registerEvents(new PlayerDropItemListener(), instance);
        pluginManager.registerEvents(new WeatherChangeListener(), instance);
        pluginManager.registerEvents(new PlayerInteractListener(), instance);
        pluginManager.registerEvents(new InventoryClickListener(), instance);
        pluginManager.registerEvents(new PlayerPickupArrowListener(), instance);
        pluginManager.registerEvents(new PotionSplashListener(), instance);
        pluginManager.registerEvents(new ProjectileLaunchListener(), instance);
        pluginManager.registerEvents(new PlayerChangeLanguageListener(), instance);
        pluginManager.registerEvents(new EntityDamageListener(), instance);
        pluginManager.registerEvents(new AntiCheatListener(), instance);
        pluginManager.registerEvents(new PlayerTeleportListener(), instance);
        pluginManager.registerEvents(new PlayerMoveListener(), instance);
        pluginManager.registerEvents(new BlockPlaceListener(), instance);
        pluginManager.registerEvents(new ExpBottleListener(), instance);
        pluginManager.registerEvents(new PlayerToggleSneakListener(), instance);

        TabListener tabListener = new TabListener(permissionManagement);
        pluginManager.registerEvents(tabListener, instance);

        eventManager.registerListener(tabListener);

        // Packet Listeners
        PacketEvents.get().registerListener(new ParticleListener());
        PacketEvents.get().registerListener(new SweepListener());

        PacketEvents.get().init();
    }
}
