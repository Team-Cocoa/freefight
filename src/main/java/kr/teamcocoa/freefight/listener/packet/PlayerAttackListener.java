package kr.teamcocoa.freefight.listener.packet;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import kr.teamcocoa.core.bukkit.utils.PacketUtils;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.world.level.GameType;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class PlayerAttackListener extends PacketListenerAbstract {

    public PlayerAttackListener() {
        super(PacketListenerPriority.NORMAL);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent e) {
        Player player = e.getPlayer();

        if (e.getPacketId() != PacketType.Play.Client.USE_ENTITY) {
            return;
        }

        WrappedPacketInUseEntity wrappedPacketInUseEntity = new WrappedPacketInUseEntity(e.getNMSPacket());

        if (wrappedPacketInUseEntity.getAction() != WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
            return;
        }

        Entity entity = wrappedPacketInUseEntity.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        Player enemy = ((Player) entity);

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
        FreeFightPlayer enemyFightPlayer = FreeFightPlayerManager.getPlayer(enemy);

        if (freeFightPlayer.getState() != GameState.SPECTATE ||
                enemyFightPlayer.getState() != GameState.INGAME ||
                freeFightPlayer.isSpectating()) {
            return;
        }

        enemyFightPlayer.getSpectators().add(freeFightPlayer);
        freeFightPlayer.setSpectating(enemyFightPlayer);

        ClientboundGameEventPacket clientboundGameEventPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.CHANGE_GAME_MODE, (float) GameType.SPECTATOR.getId());
        ClientboundSetCameraPacket clientboundSetCameraPacket = new ClientboundSetCameraPacket(((CraftPlayer) enemy).getHandle());
        PacketUtils.sendPackets(player, clientboundGameEventPacket, clientboundSetCameraPacket);
    }
}
