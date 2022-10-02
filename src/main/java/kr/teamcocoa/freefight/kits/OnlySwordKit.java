package kr.teamcocoa.freefight.kits;

import kr.teamcocoa.freefight.main.FreeFight;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OnlySwordKit extends AbstractKit {

    private static OnlySwordKit instance;

    public static OnlySwordKit getInstance() {
        if (instance == null) {
            instance = new OnlySwordKit();
        }
        return instance;
    }

    private OnlySwordKit() {
        super(Kits.ONLYSWORD);
    }

    @Override
    public void givePlayerKit(Player player) {
        ItemStack[] armorContent = new ItemStack[4];
        armorContent[3] = new ItemStack(Material.DIAMOND_HELMET);
        armorContent[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        armorContent[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
        armorContent[0] = new ItemStack(Material.DIAMOND_BOOTS);
        for (ItemStack itemStack : armorContent) {
            itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        }
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
            player.getInventory().clear();
            player.getInventory().setArmorContents(armorContent);
            player.getInventory().setItem(0, sword);
        });
    }
}
