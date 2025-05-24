package kr.teamcocoa.freefight.listener.packet;

import org.bukkit.entity.Player;

import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketListenerAbstract;
import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketListenerPriority;
import kr.teamcocoa.core.bukkit.packetevents.api.event.PacketSendEvent;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.packettype.PacketType;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.player.Equipment;
import kr.teamcocoa.core.bukkit.packetevents.api.protocol.player.EquipmentSlot;
import kr.teamcocoa.core.bukkit.packetevents.api.wrapper.play.server.WrapperPlayServerEntityEquipment;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;

public class ArmorHideListener extends PacketListenerAbstract {

    public ArmorHideListener() {
        super(PacketListenerPriority.HIGHEST);
    }

    @Override
    public void onPacketSend(PacketSendEvent e) {
        Player player = (Player) e.getPlayer();

        if(e.getPacketType() != PacketType.Play.Server.ENTITY_EQUIPMENT) {
            return;
        }

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        if(freeFightPlayer.getState() == GameState.INGAME &&
                (freeFightPlayer.getCurrentKit() == Kits.ONLYSWORD || freeFightPlayer.getCurrentKit() == Kits.SHIELD) &&
                freeFightPlayer.getSettings().isHideArmor()) {
            WrapperPlayServerEntityEquipment wrappedPacket = new WrapperPlayServerEntityEquipment(e);

            for (Equipment equipment : wrappedPacket.getEquipment()) {
                if(equipment.getSlot() == EquipmentSlot.HELMET ||
                        equipment.getSlot() == EquipmentSlot.CHEST_PLATE ||
                        equipment.getSlot() == EquipmentSlot.LEGGINGS ||
                        equipment.getSlot() == EquipmentSlot.BOOTS) {
                    equipment.getItem().setAmount(0);
                }
            }

            e.markForReEncode(true);
        }
    }
}
