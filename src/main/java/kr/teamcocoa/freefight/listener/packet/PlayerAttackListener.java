package kr.teamcocoa.freefight.listener.packet;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import kr.teamcocoa.core.bukkit.utils.PacketUtils;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import lombok.Getter;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.world.level.GameType;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerAttackListener extends PacketListenerAbstract {

    @Getter
    private static HashMap<Integer, Player> idAndPlayerMap = new HashMap<>();

    public PlayerAttackListener() {
        super(PacketListenerPriority.NORMAL);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent e) {
        Player player = ((Player) e.getPlayer());

        if(e.getPacketType() != PacketType.Play.Client.INTERACT_ENTITY) {
            return;
        }

        WrapperPlayClientInteractEntity wrappedPacket = new WrapperPlayClientInteractEntity(e);

        if(wrappedPacket.getAction() != WrapperPlayClientInteractEntity.InteractAction.ATTACK) {
            return;
        }

        Player enemy = idAndPlayerMap.get(wrappedPacket.getEntityId());

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
