package kr.teamcocoa.freefight.utils;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeadUtils {

    @Getter
    private static HashMap<UUID, String> headValueCache = new HashMap<>();

    @Getter
    private static HashMap<UUID, String> nameCache = new HashMap<>();

    public static ItemStack getHeadFromUUID(UUID uuid) {
        String value = headValueCache.getOrDefault(uuid, null);
        if(value == null) {
            throw new IllegalStateException("Cache is not ready!");
        }
        return getHeadFromUUID(value);
    }

    public static ItemStack getHeadFromUUID(String value) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        head.setItemMeta(meta);
        return head;
    }

}
