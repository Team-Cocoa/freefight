package kr.teamcocoa.freefight.kits;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.kitmanager.frontend.main.KitManager;
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
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
            player.getInventory().clear();
            KitManager.getKitManagerAPI().loadPlayerKit(player, kr.teamcocoa.kitmanager.frontend.kits.Kits.CLASSIC);
        });
    }
}
