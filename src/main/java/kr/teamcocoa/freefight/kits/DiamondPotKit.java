package kr.teamcocoa.freefight.kits;

import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.kitmanager.frontend.main.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Singleton
public class DiamondPotKit extends AbstractKit {

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
