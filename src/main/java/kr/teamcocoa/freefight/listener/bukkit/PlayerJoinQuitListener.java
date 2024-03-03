package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.core.utils.StringUtils;
import kr.teamcocoa.freefight.listener.packet.PlayerAttackListener;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import kr.teamcocoa.freefight.tab.TabManager;
import kr.teamcocoa.freefight.translation.messages.JoinPlayerMessage;
import kr.teamcocoa.freefight.translation.messages.LeavePlayerMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
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
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> TabManager.updateNameTags(player));

        JoinPlayerMessage joinPlayerMessage = new JoinPlayerMessage(player);
        for (FreeFightPlayer fightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
            Player onlinePlayer = fightPlayer.getPlayer();
            onlinePlayer.sendMessage(joinPlayerMessage.getMessage(onlinePlayer));
        }

        // entity 인스턴스를 entity id (integer) 로 얻을 수 없어서 자체 매핑을 해줘야 함
        PlayerAttackListener.getIdAndPlayerMap().put(player.getEntityId(), player);
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

        LeavePlayerMessage leavePlayerMessage = new LeavePlayerMessage(player);
        for (FreeFightPlayer fightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
            Player onlinePlayer = fightPlayer.getPlayer();
            onlinePlayer.sendMessage(leavePlayerMessage.getMessage(onlinePlayer));
        }

        // entity id (integer) 랑 entity 인스턴스 매핑한거 해제
        PlayerAttackListener.getIdAndPlayerMap().remove(player.getEntityId());

    }

}
