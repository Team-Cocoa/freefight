package kr.teamcocoa.freefight.listener.packet;

import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.event.impl.PacketPlaySendEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SweepListener extends PacketListenerAbstract {

    public SweepListener() {
        super(PacketListenerPriority.HIGHEST);
    }

    @Override
    public void onPacketPlaySend(PacketPlaySendEvent e) {
        Player player = e.getPlayer();

        if(e.getPacketId() == PacketType.Play.Server.WORLD_PARTICLES) {
            ClientboundLevelParticlesPacket rawPacket = ((ClientboundLevelParticlesPacket) e.getNMSPacket().getRawNMSPacket());

            if(rawPacket.getParticle() != ParticleTypes.SWEEP_ATTACK) {
                return;
            }

            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

            if(freeFightPlayer == null) {
                return;
            }

            if(freeFightPlayer.getState() == GameState.SPECTATE) {
                return;
            }

            Location location = player.getLocation();

            if(Math.sqrt(
                    Math.pow(location.getX() - rawPacket.getX(), 2) +
                    Math.pow(location.getY() - rawPacket.getY(), 2) +
                    Math.pow(location.getZ() - rawPacket.getZ(), 2)) >= 3) {
                e.setCancelled(true);
            }

            WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata();

        }
    }
}
