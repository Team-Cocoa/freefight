package kr.teamcocoa.freefight.listener.packet;

import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketListenerAbstract;
import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketListenerPriority;
import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketSendEvent;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.packettype.PacketType;
import kr.teamcocoa.core.bukkit.packetevents.api.util.Vector3i;
import kr.teamcocoa.core.bukkit.packetevents.api.wrapper.PacketWrapper;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.session.FreeFightSession;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
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

    @Getter
    @Setter
    private static class WrapperPlayServerWorldEvent extends PacketWrapper<WrapperPlayServerWorldEvent> {
        private int dataType;
        private Vector3i position;

        public WrapperPlayServerWorldEvent(PacketSendEvent e) {
            super(e);
        }

        public void read() {
            this.dataType = this.readInt();
            this.position = this.readBlockPosition();
        }
    }

    @Override
    public void onPacketSend(PacketSendEvent e) {

        Player player = ((Player) e.getPlayer());

        if(e.getPacketType() == PacketType.Play.Server.EFFECT) {

            WrapperPlayServerWorldEvent wrappedPacket = new WrapperPlayServerWorldEvent(e);

            // 즉시효과투척 : 2007
            // 기간효과투척 : 2002

            int type = wrappedPacket.getDataType();

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

            Vector3i vectorOfPosition = wrappedPacket.getPosition();

            BlockPos position = new BlockPos(vectorOfPosition.x, vectorOfPosition.y, vectorOfPosition.z);

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
