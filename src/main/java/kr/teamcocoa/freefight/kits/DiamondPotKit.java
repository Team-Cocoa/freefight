package kr.teamcocoa.freefight.kits;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.kitmanager.frontend.main.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
           kitAPI.loadPlayerKit(player, kr.teamcocoa.kitmanager.backend.Kits.DIAMOND_POT);
        });

    }
}
