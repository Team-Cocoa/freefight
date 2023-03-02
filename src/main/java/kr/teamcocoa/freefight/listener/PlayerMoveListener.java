package kr.teamcocoa.freefight.listener;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        handleFallVoid(e);
    }

    private void handleFallVoid(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
        if(freeFightPlayer == null) {
            return;
        }

        Location location = e.getTo();
        if(location.getY() < 0) {
            freeFightPlayer.moveToSpawn();
        }
    }

}
