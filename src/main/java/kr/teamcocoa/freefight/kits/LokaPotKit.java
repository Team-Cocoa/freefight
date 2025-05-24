package kr.teamcocoa.freefight.kits;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.kitmanager.frontend.main.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LokaPotKit extends AbstractKit {

    private static LokaPotKit instance;

    public static LokaPotKit getInstance() {
        if(instance == null) {
            instance = new LokaPotKit();
        }
        return instance;
    }

    private LokaPotKit() {
        super(Kits.LOKA_POT);
    }

    @Override
    public void givePlayerKit(Player player) {
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
            player.getInventory().clear();
            kitAPI.loadPlayerKit(player, kr.teamcocoa.kitmanager.backend.Kits.LOKA);
        });
    }

}
