package kr.teamcocoa.freefight.listener;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {

    @EventHandler
    public void onHeal(EntityRegainHealthEvent e) {
        if(e.getEntity() instanceof Player player) {
            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
            FreeFightSession freeFightSession = SessionManager.getSession(freeFightPlayer);
            if(e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
                if(freeFightSession != null && freeFightSession.getKits() == Kits.DIAMOND_POT) {
                    return;
                }
                e.setCancelled(true);
            }
        }
    }

}
