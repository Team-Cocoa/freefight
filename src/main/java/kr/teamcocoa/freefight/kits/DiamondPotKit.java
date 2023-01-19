package kr.teamcocoa.freefight.kits;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.utils.ItemUtils;
import kr.teamcocoa.freefight.utils.Pair;
import kr.teamcocoa.kitmanager.frontend.main.KitManager;
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
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
           player.getInventory().clear();
           KitManager.getKitManagerAPI().loadPlayerKit(player, kr.teamcocoa.kitmanager.frontend.kits.Kits.DIAMOND_POT);
        });

    }
}
