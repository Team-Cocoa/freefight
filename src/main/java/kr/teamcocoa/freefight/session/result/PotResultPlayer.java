package kr.teamcocoa.freefight.session.result;

import kr.teamcocoa.freefight.utils.Serializer;
import kr.teamcocoa.kitmanager.frontend.utils.SerialUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.UUID;

@Getter
public class PotResultPlayer extends ResultPlayer {

    private static ItemStack healPotion;

    static {
        healPotion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta healPotionMeta = (PotionMeta) healPotion.getItemMeta();
        healPotionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        healPotion.setItemMeta(healPotionMeta);
    }

    private int leftPot;

    public PotResultPlayer(
            UUID uuid,
            double health,
            double hunger,
            double saturation,
            double damageInComing,
            double damageOutComing,
            byte[] serializedInventory) {
        super(uuid, health, hunger, saturation, damageInComing, damageOutComing);
        ItemStack[] itemStacks = Serializer.bytesToItemStacks(serializedInventory);
        this.leftPot = (int) Arrays.stream(itemStacks).filter(healPotion::equals).count();
    }



}
