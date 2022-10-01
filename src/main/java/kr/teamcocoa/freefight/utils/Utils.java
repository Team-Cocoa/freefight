package kr.teamcocoa.freefight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static void catchSynchronous() {
        if(Thread.currentThread() == MinecraftServer.getServer().serverThread) {
            throw new IllegalStateException("[FreeFight] Synchronous Called!");
        }
    }

    public static void catchAsynchronous() {
        if(Thread.currentThread() != MinecraftServer.getServer().serverThread) {
            throw new IllegalStateException("[FreeFight] Asynchronous Called!");
        }
    }

    public static ItemStack getBackground() {
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES });
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
