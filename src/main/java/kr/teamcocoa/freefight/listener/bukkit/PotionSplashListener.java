package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.freefight.listener.packet.ParticleListener;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import net.minecraft.core.BlockPos;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftThrownPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SplashPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PotionSplashListener implements Listener {

    @EventHandler
    public void onSplash(PotionSplashEvent e) {
        if(e.getPotion().getShooter() instanceof Player player) {
            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
            if(freeFightPlayer == null) {
                return;
            }

            FreeFightSession session = SessionManager.getSession(freeFightPlayer);
            if(session == null) {
                return;
            }

            for (LivingEntity affectedEntity : e.getAffectedEntities()) {
                if(affectedEntity instanceof Player affectedPlayer) {
                    if(session.getFreeFightPlayer1().getPlayer() != affectedPlayer && session.getFreeFightPlayer2().getPlayer() != affectedPlayer) {
                        e.setIntensity(affectedEntity, 0);
                    }
                }
                else {
                    e.setIntensity(affectedEntity, 0);
                }
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if(session.getFreeFightPlayer1().getPlayer() != onlinePlayer &&
                        session.getFreeFightPlayer2().getPlayer() != onlinePlayer) {
                    onlinePlayer.hideEntity(FreeFight.getInstance(), e.getEntity());
                }
            }

            BlockPos pos = ((CraftThrownPotion) e.getPotion()).getHandle().blockPosition();

            ParticleListener.add(pos, session);

        }
    }

}
