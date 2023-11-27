package kr.teamcocoa.freefight.listener.bukkit;

import com.destroystokyo.paper.event.entity.ExperienceOrbMergeEvent;
import kr.teamcocoa.freefight.listener.packet.ParticleListener;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftThrownExpBottle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ExpBottleListener implements Listener {

    /*
        뭔가 까먹을꺼 같으니 주석을 남기도록 하지
        자신이 던진 인첸트 병의 exp orb 는 자신이 속한 세션에서만 유효 해야함.
        먼저 인첸트 병을 던져서 바닥에서 깨지면
        ExpBottleEvent 가 발생한다.
        여기서 바닥에 닿은 지점을 orbTable 에다가 넣는다.
        바닥에서 깨지면 exp orb 가 나온다.
        이때 엔티티 생성 이벤트인 EntitySpawnEvent 가 발생하고,
        이때 entity 의 좌표를 기반으로

     */

    private static HashMap<ExperienceOrb, FreeFightSession> orbTable = new HashMap<>();
    private static HashMap<Vec3, Player> playerTable = new HashMap<>();

    @EventHandler
    public void onThrow(ExpBottleEvent e) {
        CraftThrownExpBottle thrownExpBottle = ((CraftThrownExpBottle) e.getEntity());
        if(!(thrownExpBottle.getShooter() instanceof Player)) {
            return;
        }

        Player player = ((Player) thrownExpBottle.getShooter());

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
        if(freeFightPlayer == null) {
            e.setCancelled(true);
            return;
        }

        FreeFightSession session = SessionManager.getSession(freeFightPlayer);
        if(session == null) {
            e.setCancelled(true);
            return;
        }

        int exp = e.getExperience();

        e.setExperience(0); // cancel exp orb spawning

        Location location = thrownExpBottle.getLocation();

        net.minecraft.world.entity.ExperienceOrb orb = new net.minecraft.world.entity.ExperienceOrb(
                ((CraftWorld) e.getEntity().getWorld()).getHandle(),
                location.getX(), location.getY(), location.getZ(), exp);

        try {
            Class clazz = orb.getClass();

            Method repairPlayerItems = clazz.getDeclaredMethod("a", net.minecraft.world.entity.player.Player.class, int.class);
            repairPlayerItems.setAccessible(true);
            int i = ((Integer) repairPlayerItems.invoke(orb, ((CraftPlayer) player).getHandle(), exp));

            if (i > 0) {
                ((CraftPlayer) player).getHandle().giveExperiencePoints(i); // CraftBukkit - this.value -> event.getAmount() // Paper - supply experience orb object
            }
        }
        catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e1) {
            e1.printStackTrace();
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(session.getFreeFightPlayer1().getPlayer() != onlinePlayer &&
                    session.getFreeFightPlayer2().getPlayer() != onlinePlayer) {
                onlinePlayer.hideEntity(FreeFight.getInstance(), e.getEntity());
            }
        }

        BlockPos pos = ((CraftThrownExpBottle) e.getEntity()).getHandle().blockPosition();

        ParticleListener.add(pos, session);

    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof ExperienceOrb) {
            ExperienceOrb orb = (ExperienceOrb) entity;
            Location location = e.getLocation();
            Bukkit.getLogger().info(location.toString());
            Vec3 vec3 = new Vec3(location.getX(), location.getY(), location.getZ());

            Player player = playerTable.getOrDefault(vec3, null);

            if(player != null) {
                Bukkit.getLogger().info("Orb Spawn Location : " + vec3.toString());
                Bukkit.getLogger().info("Orb origin : " + player);
                playerTable.remove(vec3);

                FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

                if(freeFightPlayer == null) {
                    return;
                }

                FreeFightSession session = SessionManager.getSession(freeFightPlayer);

                if(session == null) {
                    return;
                }

                orbTable.put(orb, session);
            }
        }
    }

    @EventHandler
    public void onEntity(EntityTargetLivingEntityEvent e) {
        if(!(e.getEntity() instanceof ExperienceOrb)) {
            return;
        }

        ExperienceOrb orb = ((ExperienceOrb) e.getEntity());

        if(!(e.getTarget() instanceof Player)) {
            return;
        }

        FreeFightSession freeFightSession = orbTable.getOrDefault(orb, null);

        if(freeFightSession == null) {
            Bukkit.getLogger().info("Cant get session");
            return;
        }

        Bukkit.getLogger().info("sessionPlayer1 : " + freeFightSession.getFreeFightPlayer1().getPlayer());
        Bukkit.getLogger().info("sessionPlayer2 : " + freeFightSession.getFreeFightPlayer2().getPlayer());

        Player target = ((Player) e.getTarget());

        if(target != freeFightSession.getFreeFightPlayer1().getPlayer() && target != freeFightSession.getFreeFightPlayer2().getPlayer()) {
            Bukkit.getLogger().info("detected invalid player.");
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onMerge(ExperienceOrbMergeEvent e) {
        ExperienceOrb beforeOrb = e.getMergeSource();
        FreeFightSession session = orbTable.getOrDefault(beforeOrb, null);

        if(session == null) {
            return;
        }

        orbTable.remove(beforeOrb);
        orbTable.put(e.getMergeTarget(), session);
    }

    @EventHandler
    public void onExpGet(PlayerExpChangeEvent e) {
        Player player = e.getPlayer();
        Bukkit.getLogger().info("got exp change player : " + player);
        ExperienceOrb orb = ((ExperienceOrb) e.getSource());

        orbTable.remove(orb);
    }

}
