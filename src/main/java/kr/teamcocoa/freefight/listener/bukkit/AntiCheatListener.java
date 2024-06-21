package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.core.bukkit.events.anticheat.AntiCheatFlagEvent;
import kr.teamcocoa.core.bukkit.events.anticheat.AntiCheatPunishEvent;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.replay.LogType;
import kr.teamcocoa.freefight.replay.SessionReplay;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class AntiCheatListener implements Listener {

    private DecimalFormat format = new DecimalFormat("##.##");

    @EventHandler
    public void onFlag(AntiCheatFlagEvent e) {

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

        session.setAntiCheatDetect(true);
        session.getDetectedAntiCheatFlags().add(e.getFlag().getName() + " (" + e.getFlag().getType() + ")");

        SessionReplay sessionReplay = session.getSessionReplay();

        sessionReplay.addMessage(LogType.ANTI_CHEAT,
                MessageFormat.format("{0} failed {1} (Type {2}) [ {3} / {4} ] | {5}TPS",
                    player.getName(),
                    e.getFlag().getName(),
                    e.getFlag().getType(),
                    e.getFlag().getVl() + 1,
                    e.getFlag().getMaxVl(),
                    format.format(((CraftServer) Bukkit.getServer()).getServer().recentTps[0])));

    }

    @EventHandler
    public void onBan(AntiCheatPunishEvent e) {

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
