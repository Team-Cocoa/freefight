package kr.teamcocoa.freefight.listener;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();
        boolean saved = FreeFightPlayerManager.addPlayer(player);
        if(!saved) {
            Bukkit.getLogger().info("[FreeFight] FreeFightPlayerManager.addPlayer(Player player) returned false! class : PlayerJoinQuitListener");
            player.kick(Component.text(StringUtils.color(
                    FreeFight.getPrefix() + "&cFailed to init FreeFightPlayer! Contact to developer!")));
        }
        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
        freeFightPlayer.join();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player player = e.getPlayer();
        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        FreeFightSession session = SessionManager.getSession(freeFightPlayer);

        if(session != null) {
            session.stop(freeFightPlayer);
        }

        freeFightPlayer.quit();
        boolean removed = FreeFightPlayerManager.removePlayer(player);
        if(!removed) {
            Bukkit.getLogger().info("[FreeFight] FreeFightPlayerManager.removePlayer(Player player) returned false! class : PlayerJoinQuitListener");
        }
    }

}
