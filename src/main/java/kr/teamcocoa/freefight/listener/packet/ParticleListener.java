package kr.teamcocoa.freefight.listener.packet;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.event.impl.PacketPlaySendEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.session.FreeFightSession;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ParticleListener extends PacketListenerAbstract {

    @Getter
    private static HashMap<BlockPos, FreeFightSession> splashValid = new HashMap<>();

    public static void add(BlockPos blockPos, FreeFightSession session) {
        splashValid.put(blockPos, session);
        Bukkit.getScheduler().runTaskLater(FreeFight.getInstance(), () -> splashValid.remove(blockPos), 2L);
    }

    public ParticleListener() {
        super(PacketListenerPriority.HIGHEST);
    }

    @Override
    public void onPacketPlaySend(PacketPlaySendEvent e) {
        Player player = e.getPlayer();

        if(e.getPacketId() == PacketType.Play.Server.WORLD_EVENT) {
            ClientboundLevelEventPacket rawPacket = ((ClientboundLevelEventPacket) e.getNMSPacket().getRawNMSPacket());

            // 즉시효과투척 : 2007
            // 기간효과투척 : 2002

            int type = rawPacket.getType();

            if(type != 2007 && type != 2002) {
                return;
            }

            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

            if(freeFightPlayer == null) {
                return;
            }

            if(freeFightPlayer.getState() == GameState.SPECTATE) {
                return;
            }

            BlockPos position = rawPacket.getPos();

            FreeFightSession session = splashValid.getOrDefault(position, null);

            if(session == null) {
                return;
            }

            if(player != session.getFreeFightPlayer1().getPlayer() && player != session.getFreeFightPlayer2().getPlayer()) {
                e.setCancelled(true);
            }
        }
    }

}
