package kr.teamcocoa.freefight.kits;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.kitmanager.frontend.main.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NetheritePotKit extends AbstractKit {

    private static NetheritePotKit instance;

    public static NetheritePotKit getInstance() {
        if(instance == null) {
            instance = new NetheritePotKit();
        }
        return instance;
    }

    private NetheritePotKit() {
        super(Kits.NETHERITE_POT);
    }

    @Override
    public void givePlayerKit(Player player) {
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
            player.getInventory().clear();
            KitManager.getKitManagerAPI().loadPlayerKit(player, kr.teamcocoa.kitmanager.frontend.kits.Kits.NETHERITE_POT);
        });
    }

}
