package kr.teamcocoa.freefight.listener;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        handleFall(e);
        handlePreGameDamage(e);
    }

    private void handleFall(EntityDamageEvent e) {

        if(e.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = ((Player) e.getEntity());

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        if(freeFightPlayer != null) {
            e.setCancelled(true);
        }

    }

    private void handlePreGameDamage(EntityDamageEvent e) {
        Player player = ((Player) e.getEntity());

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        if(freeFightPlayer == null) {
            return;
        }

        if(freeFightPlayer.getState() != GameState.INGAME) {
            e.setCancelled(true);
            return;
        }

        FreeFightSession session = SessionManager.getSession(freeFightPlayer);

        if(session == null) {
            return;
        }

        if(!session.isDamageAble()) {
            e.setCancelled(true);
        }
    }

}
