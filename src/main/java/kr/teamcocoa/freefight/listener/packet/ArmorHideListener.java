package kr.teamcocoa.freefight.listener.packet;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.Equipment;
import com.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEquipment;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import org.bukkit.entity.Player;

public class ArmorHideListener extends PacketListenerAbstract {

    public ArmorHideListener() {
        super(PacketListenerPriority.HIGHEST);
    }

    private static final ItemStack airItem = new ItemStack.Builder().type(ItemTypes.AIR).build();

    @Override
    public void onPacketSend(PacketSendEvent e) {

        Player player = (Player) e.getPlayer();

        if(e.getPacketType() != PacketType.Play.Server.ENTITY_EQUIPMENT) {
            return;
        }

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        if(freeFightPlayer.getState() == GameState.INGAME &&
                (freeFightPlayer.getCurrentKit() == Kits.ONLYSWORD || freeFightPlayer.getCurrentKit() == Kits.SHIELD)) {
            WrapperPlayServerEntityEquipment wrappedPacket = new WrapperPlayServerEntityEquipment(e);

            for (Equipment equipment : wrappedPacket.getEquipment()) {
                if(equipment.getSlot() == EquipmentSlot.HELMET ||
                        equipment.getSlot() == EquipmentSlot.CHEST_PLATE ||
                        equipment.getSlot() == EquipmentSlot.LEGGINGS ||
                        equipment.getSlot() == EquipmentSlot.BOOTS) {
                    equipment.setItem(airItem);
                }
            }

        }

    }
}
