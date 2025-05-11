package kr.teamcocoa.freefight.storages;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import kr.teamcocoa.core.network.cache.Cache;
import kr.teamcocoa.freefight.player.FreeFightStat;
import kr.teamcocoa.freefight.settings.FreeFightSetting;

public class FreeFightPlayerStorage {
    
    private static Cache<UUID, FreeFightStat> uuidToStats = new Cache<>(1, TimeUnit.DAYS);

    private static Cache<UUID, FreeFightSetting> uuidToSettings = new Cache<>(1, TimeUnit.DAYS);

    public static void registerStat(UUID uuid, FreeFightStat stat) {
        if(uuidToStats.readData(uuid) != null) {
            return;
        }

        uuidToStats.createData(uuid, stat);
    }

    public static void registerSetting(UUID uuid, FreeFightSetting setting) {
        if(uuidToSettings.readData(uuid) != null) {
            return;
        }

        uuidToSettings.createData(uuid, setting);
    }

    public static FreeFightStat getStatByUUID(UUID uuid) {
        return uuidToStats.readData(uuid);
    }

    public static FreeFightSetting getSettingByUUID(UUID uuid) {
        return uuidToSettings.readData(uuid);
    }

}
