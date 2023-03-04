package kr.teamcocoa.freefight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
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

    public static void sendBar(Player player, String message) {
        MutableComponent msg = Component.Serializer.fromJson("{\"text\": \"" + message + "\"}");
        ClientboundChatPacket packet = new ClientboundChatPacket(msg, ChatType.GAME_INFO, player.getUniqueId());
        sendPackets(player, packet);
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

}

