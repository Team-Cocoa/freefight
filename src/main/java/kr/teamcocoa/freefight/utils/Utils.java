package kr.teamcocoa.freefight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.server.MinecraftServer;

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

}
