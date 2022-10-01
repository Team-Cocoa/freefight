package kr.teamcocoa.freefight.listener;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getPlayer();
        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        if(freeFightPlayer == null) {
            return;
        }

        FreeFightSession session = SessionManager.getSession(freeFightPlayer);

        if(session == null) {
            return;
        }

        if(session.isRunning()) {
            e.setCancelled(true);
            session.stop(freeFightPlayer);
        }
    }

}
