package kr.teamcocoa.freefight.listener.packet;

import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketListenerAbstract;
import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketSendEvent;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.entity.data.EntityData;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.entity.data.EntityDataTypes;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.entity.data.EntityMetadataProvider;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.item.ItemStack;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.packettype.PacketType;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.packettype.PacketTypeCommon;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.player.Equipment;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.player.User;
import kr.teamcocoa.core.bukkit.packetevents.api.wrapper.play.server.WrapperPlayServerEntityEquipment;
import kr.teamcocoa.core.bukkit.packetevents.api.wrapper.play.server.WrapperPlayServerEntityMetadata;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HideDamageListener extends PacketListenerAbstract {

    @Override
    public void onPacketSend(PacketSendEvent e) {
        PacketTypeCommon packetType = e.getPacketType();

        if((packetType != PacketType.Play.Server.ENTITY_METADATA) &&
                (packetType != PacketType.Play.Server.ENTITY_EQUIPMENT)) {
            return;
        }

        User user = e.getUser();
        int entityId = user.getEntityId();
        UUID uuid = user.getUUID();

        Player bukkitPlayer = Bukkit.getPlayer(uuid);

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(bukkitPlayer);

        if(freeFightPlayer == null) {
            return;
        }

        if(freeFightPlayer.getState() != GameState.INGAME) {
            return;
        }

        if(freeFightPlayer.getCurrentKit() == Kits.NETHERITE_POT) {
            return;
        }

        if (packetType == PacketType.Play.Server.ENTITY_METADATA) {
            WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata(e);


            if(wrapper.getEntityId() == entityId) {
                return;
            }

            // 조건 넣어야 할듯

            for (EntityData entityMetadata : wrapper.getEntityMetadata()) {
                if((entityMetadata.getIndex() == 9 || entityMetadata.getIndex() == 15) &&
                        entityMetadata.getValue() instanceof Float) {
                    entityMetadata.setValue(0.5f);
                    e.markForReEncode(true);
                }
            }

        }
        if (packetType == PacketType.Play.Server.ENTITY_EQUIPMENT) {
            WrapperPlayServerEntityEquipment wrapper = new WrapperPlayServerEntityEquipment(e);

            if(wrapper.getEntityId() == entityId) {
                return;

            }

            for (Equipment equipment : wrapper.getEquipment()) {
                ItemStack item = equipment.getItem();
                item.setDamageValue(item.getMaxDamage());
            }

            e.markForReEncode(true);
        }

    }
}
