package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player player) {
            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
            if(freeFightPlayer == null) {
                e.setCancelled(true);
                return;
            }
            FreeFightSession session = SessionManager.getSession(freeFightPlayer);
            if(session == null) {
                e.setCancelled(true);
                return;
            }
            e.setCancelled(
                    freeFightPlayer.getCurrentKit() != Kits.DIAMOND_POT &&
                            freeFightPlayer.getCurrentKit() != Kits.NETHERITE_POT &&
                            freeFightPlayer.getCurrentKit() != Kits.LOKA_POT);
        }

    }

}
