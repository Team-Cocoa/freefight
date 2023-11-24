package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {

    @EventHandler
    public void onToggle(PlayerToggleSneakEvent e) {
        handleSpectate(e);
    }

    private void handleSpectate(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        if(freeFightPlayer == null) {
            return;
        }

        if(freeFightPlayer.isSpectating()) {
            freeFightPlayer.stopSpectate();
        }

    }

}
