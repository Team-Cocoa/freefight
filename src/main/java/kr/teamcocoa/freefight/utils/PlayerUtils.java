package kr.teamcocoa.freefight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerUtils {

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {

        ClientboundSetTitlesAnimationPacket timePacket = new ClientboundSetTitlesAnimationPacket(fadeIn, stay, fadeOut);
        ClientboundSetTitleTextPacket titlePacket = new ClientboundSetTitleTextPacket(Component.Serializer.fromJson("{\"text\": \"" + StringUtils.color(title) + "\"}"));
        ClientboundSetSubtitleTextPacket subTitlePacket = new ClientboundSetSubtitleTextPacket(Component.Serializer.fromJson("{\"text\": \"" + StringUtils.color(subtitle) + "\"}"));

        sendPackets(player, timePacket, titlePacket, subTitlePacket);
    }

    public static void sendPackets(Player player, Packet... packets) {
        try {
            ServerGamePacketListenerImpl playerConnection = ((CraftPlayer) player).getHandle().connection;
            for(Packet packet : packets) {
                playerConnection.connection.send(packet);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendFakeLightning(Player player, double x, double y, double z) {
        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, ((CraftWorld) player.getWorld()).getHandle());
        lightningBolt.getAddEntityPacket();
        ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, x, y, z, 10000F, 63);
        sendPackets(player, lightningBolt.getAddEntityPacket(), clientboundSoundPacket);
    }

}

