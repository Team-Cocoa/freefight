package kr.teamcocoa.freefight.listener.packet;

//import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketListenerAbstract;
import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketListenerPriority;
import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketSendEvent;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.packettype.PacketType;
import kr.teamcocoa.core.bukkit.packetevents.api.wrapper.play.server.WrapperPlayServerParticle;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import net.minecraft.core.particles.ParticleTypes;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SweepListener extends PacketListenerAbstract {

    public SweepListener() {
        super(PacketListenerPriority.HIGHEST);
    }

    @Override
    public void onPacketSend(PacketSendEvent e) {
        Player player = ((Player) e.getPlayer());

        if(e.getPacketType() == PacketType.Play.Server.PARTICLE) {
            WrapperPlayServerParticle wrappedPacket = new WrapperPlayServerParticle(e);

            if(wrappedPacket.getParticle().getType() != ParticleTypes.SWEEP_ATTACK) {
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
                    Math.pow(location.getX() - wrappedPacket.getPosition().getX(), 2) +
                            Math.pow(location.getY() - wrappedPacket.getPosition().getY(), 2) +
                            Math.pow(location.getZ() - wrappedPacket.getPosition().getZ(), 2)) >= 3) {
                e.setCancelled(true);
            }

//            WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata();

        }
    }

}
