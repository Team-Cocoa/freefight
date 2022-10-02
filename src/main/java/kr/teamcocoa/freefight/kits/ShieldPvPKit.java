package kr.teamcocoa.freefight.kits;

import kr.teamcocoa.freefight.main.FreeFight;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShieldPvPKit extends AbstractKit {

    private static ShieldPvPKit instance;

    public static ShieldPvPKit getInstance() {
        if (instance == null) {
            instance = new ShieldPvPKit();
        }
        return instance;
    }

    private ShieldPvPKit() {
        super(Kits.SHIELD);
    }

    @Override
    public void givePlayerKit(Player player) {
        ItemStack[] armorContent = new ItemStack[4];
        armorContent[3] = new ItemStack(Material.DIAMOND_HELMET);
        armorContent[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        armorContent[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
        armorContent[0] = new ItemStack(Material.DIAMOND_BOOTS);

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        ItemStack crossbow = new ItemStack(Material.CROSSBOW);
        ItemStack bow = new ItemStack(Material.BOW);
        ItemStack arrow = new ItemStack(Material.ARROW, 7);
        ItemStack shield = new ItemStack(Material.SHIELD);
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
            player.getInventory().clear();
            player.getInventory().setArmorContents(armorContent);
            player.getInventory().setItemInOffHand(shield);
            player.getInventory().setItem(0, sword);
            player.getInventory().setItem(1, axe);
            player.getInventory().setItem(2, crossbow);
            player.getInventory().setItem(3, bow);
            player.getInventory().setItem(4, arrow);
        });
    }
}
