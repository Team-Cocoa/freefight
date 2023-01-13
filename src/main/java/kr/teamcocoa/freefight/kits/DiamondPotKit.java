package kr.teamcocoa.freefight.kits;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.utils.ItemUtils;
import kr.teamcocoa.freefight.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiamondPotKit extends AbstractKit {

    private static DiamondPotKit instance;

    public static DiamondPotKit getInstance() {
        if (instance == null) {
            instance = new DiamondPotKit();
        }
        return instance;
    }

    private DiamondPotKit() {
        super(Kits.DIAMOND_POT);
    }

    @Override
    public void givePlayerKit(Player player) {
        /*
        * 갑옷 부분 시작
        * */
        List<Pair<Enchantment, Integer>> armorEnchantList = new ArrayList<>(3);
        armorEnchantList.add(new Pair<>(Enchantment.MENDING, 1));
        armorEnchantList.add(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 4));
        armorEnchantList.add(new Pair<>(Enchantment.DURABILITY, 3));

        ItemStack[] armorContent = new ItemStack[4];
        armorContent[3] = new ItemStack(Material.DIAMOND_HELMET);
        armorContent[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        armorContent[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
        armorContent[0] = new ItemStack(Material.DIAMOND_BOOTS);
        for (ItemStack itemStack : armorContent) {
            ItemUtils.enchant(itemStack, armorEnchantList);
        }

        /*
         * 인벤 부분 시작
         */
        ItemStack[] inventory = new ItemStack[36];

        List<Pair<Enchantment, Integer>> swordEnchantList = new ArrayList<>();
        swordEnchantList.add(new Pair<>(Enchantment.DAMAGE_ALL, 5));
        swordEnchantList.add(new Pair<>(Enchantment.DURABILITY, 3));
        ItemStack sword = ItemUtils.enchant(new ItemStack(Material.DIAMOND_SWORD), swordEnchantList);

        ItemStack pearl = new ItemStack(Material.ENDER_PEARL, 16);

        ItemStack regen = new ItemStack(Material.SPLASH_POTION);
        PotionMeta regenPotionMeta = (PotionMeta) regen.getItemMeta();
        regenPotionMeta.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
        regen.setItemMeta(regenPotionMeta);

        ItemStack speed = new ItemStack(Material.SPLASH_POTION);
        PotionMeta speedPotionMeta = (PotionMeta) speed.getItemMeta();
        speedPotionMeta.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        speed.setItemMeta(speedPotionMeta);

        ItemStack strength = new ItemStack(Material.SPLASH_POTION);
        PotionMeta strengthPotionMeta = (PotionMeta) speed.getItemMeta();
        strengthPotionMeta.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
        strength.setItemMeta(strengthPotionMeta);

        ItemStack heal = new ItemStack(Material.SPLASH_POTION);
        PotionMeta healPotionMeta = (PotionMeta) speed.getItemMeta();
        healPotionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        heal.setItemMeta(healPotionMeta);

        inventory[0] = sword;
        inventory[1] = pearl;

        for(Integer i : Arrays.asList(6, 15, 24, 33)) {
            inventory[i] = regen;
        }

        for (Integer i : Arrays.asList(7, 16, 25, 34)) {
            inventory[i] = speed;
        }

        for (Integer i : Arrays.asList(8, 17, 26, 35)) {
            inventory[i] = strength;
        }

        for(int i = 0; i < inventory.length; i++) {
            if(inventory[i] == null) {
                inventory[i] = heal;
            }
        }

        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
           player.getInventory().clear();
           player.getInventory().setContents(inventory);
           player.getInventory().setArmorContents(armorContent);
           player.getInventory().setItemInOffHand(new ItemStack(Material.GOLDEN_CARROT, 32));
        });

    }
}
