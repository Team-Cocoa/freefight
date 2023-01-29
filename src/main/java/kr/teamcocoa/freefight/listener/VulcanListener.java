package kr.teamcocoa.freefight.listener;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.replay.LogType;
import kr.teamcocoa.freefight.replay.SessionReplay;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import me.frep.vulcan.api.event.VulcanFlagEvent;
import me.frep.vulcan.api.event.VulcanPunishEvent;
import net.minecraft.server.MinecraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class VulcanListener implements Listener {

    private DecimalFormat format = new DecimalFormat("##.##");

    @EventHandler
    public void onFlag(VulcanFlagEvent e) {

        Player player = e.getPlayer();
        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        if(freeFightPlayer == null || freeFightPlayer.getState() != GameState.INGAME) {
            return;
        }

        FreeFightSession session = SessionManager.getSession(freeFightPlayer);

        if(session == null ||
                session.getSessionReplay() == null ||
                !session.getSessionReplay().getReplay().isRecording()) {
            return;
        }

        SessionReplay sessionReplay = session.getSessionReplay();

        sessionReplay.addMessage(LogType.ANTI_CHEAT,
                MessageFormat.format("{0} failed {1} (Type {2}) [ {3} / {4} ] | {5}TPS",
                    player.getName(),
                    e.getCheck().getName(),
                    e.getCheck().getType(),
                    e.getCheck().getVl() + 1,
                    e.getCheck().getMaxVl(),
                    format.format(MinecraftServer.getServer().recentTps[0])));

    }

    @EventHandler
    public void onBan(VulcanPunishEvent e) {

        Player player = e.getPlayer();
        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        if(freeFightPlayer == null || freeFightPlayer.getState() != GameState.INGAME) {
            return;
        }

        FreeFightSession session = SessionManager.getSession(freeFightPlayer);

        if(session == null ||
                session.getSessionReplay() == null ||
                !session.getSessionReplay().getReplay().isRecording()) {
            return;
        }

        SessionReplay sessionReplay = session.getSessionReplay();

        sessionReplay.addMessage(LogType.ANTI_CHEAT, player.getName() + " is banned from the server.");

    }

}
