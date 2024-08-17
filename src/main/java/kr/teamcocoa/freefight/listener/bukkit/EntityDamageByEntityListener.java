package kr.teamcocoa.freefight.listener.bukkit;

import kr.teamcocoa.core.bukkit.utils.ComponentUtils;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.replay.LogType;
import kr.teamcocoa.freefight.replay.SessionReplay;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import kr.teamcocoa.freefight.translation.items.ChallengerTitle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class EntityDamageByEntityListener implements Listener {

    private DecimalFormat format = new DecimalFormat("##.####");

    private double getDistance(Location location1, Location location2) {
        return Math.sqrt(
                Math.pow(location1.getX() - location2.getX(), 2) +
                Math.pow(location1.getY() - location2.getY(), 2) +
                Math.pow(location1.getZ() - location2.getZ(), 2));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        projectileDamageHandle(e);
        damageHandle(e);
        challengerHandle(e);
    }

    private void damageHandle(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
        if(freeFightPlayer == null) {
            return;
        }

        if(freeFightPlayer.getState() != GameState.INGAME) {
            e.setCancelled(true);
        }

        if(!(e.getDamager() instanceof Player)) {
            return;
        }

        Player enemy = (Player) e.getDamager();
        FreeFightPlayer enemyFreeFightPlayer = FreeFightPlayerManager.getPlayer(enemy);
        if(enemyFreeFightPlayer == null) {
            return;
        }

        if(enemyFreeFightPlayer.getState() != GameState.INGAME) {
            e.setCancelled(true);
        }

        FreeFightSession session = SessionManager.getSession(freeFightPlayer);
        if(session != null) {
            if(!session.isDamageAble()) {
                e.setCancelled(true);
                return;
            }

            FreeFightPlayer sessionEnemy = session.getFreeFightPlayer1() == freeFightPlayer ? session.getFreeFightPlayer2() : session.getFreeFightPlayer1();
            if(sessionEnemy != enemyFreeFightPlayer) {
                e.setCancelled(true);
                return;
            }

            SessionReplay sessionReplay = session.getSessionReplay();

            Location enemyLocation = enemy.getLocation();
            Location damagerLocation = player.getLocation();

            Location enemyEyeLocation = enemy.getEyeLocation();
            Location damagerEyeLocation = player.getEyeLocation();

            sessionReplay.addMessage(LogType.ANTI_CHEAT,
                    MessageFormat.format("{0}({1}ms) hit {2}({3}ms) coord {4} | head {5} | {6} TPS",
                            enemy.getName(),
                            enemy.getPing(),
                            player.getName(),
                            player.getPing(),
                            format.format(enemyLocation.distance(damagerLocation)),
                            format.format(getDistance(enemyEyeLocation, damagerEyeLocation)),
                            format.format(((CraftServer) Bukkit.getServer()).getServer().recentTps[0])));

            if(session.getKits() == Kits.LOKA_POT) {
//                Bukkit.getLogger().info(MessageFormat.format(
//                        "Before multi Hitter : {0} Victim : {1} Damage : {2} FinalDamage : {3}",
//                        enemy.getName(), player.getName(), e.getDamage(), e.getFinalDamage()));
                e.setDamage(e.getDamage() * 1.333);
//                Bukkit.getLogger().info(MessageFormat.format(
//                        "After multi Hitter : {0} Victim : {1} Damage : {2} FinalDamage : {3}",
//                        enemy.getName(), player.getName(), e.getDamage(), e.getFinalDamage()));
            }

            sessionEnemy.addDamageOut(e.getFinalDamage());
            freeFightPlayer.addDamageIn(e.getFinalDamage());
            if(player.getHealth() - e.getFinalDamage() <= 0.0) {
                if(player.getInventory().getItemInMainHand().getType() != Material.TOTEM_OF_UNDYING && player.getInventory().getItemInOffHand().getType() != Material.TOTEM_OF_UNDYING) {
                    e.setCancelled(true);
                    session.stop(freeFightPlayer);
                }
            }

            if(session.getMatchTask() != null) {
                session.getMatchTask().setHitted(true);
            }

        }
    }

    private void challengerHandle(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        Player enemy = (Player) e.getDamager();

        ItemStack mainHandItem = enemy.getInventory().getItemInMainHand();

        if(mainHandItem.hasItemMeta()
                && mainHandItem.getItemMeta().hasDisplayName()
                && ComponentUtils.componentEquals(mainHandItem.getItemMeta().displayName(), ChallengerTitle.getInstance().getMessage(enemy))) {
            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
            FreeFightPlayer enemyFreeFightPlayer = FreeFightPlayerManager.getPlayer(enemy);

            enemyFreeFightPlayer.challenge(freeFightPlayer);
        }
    }

    public void projectileDamageHandle(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Projectile)) {
            return;
        }
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        Projectile projectile = (Projectile) e.getDamager();

        if(!(projectile.getShooter() instanceof Player)) {
            return;
        }

        Player shooter = (Player) projectile.getShooter();

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
        FreeFightPlayer enemyFightPlayer = FreeFightPlayerManager.getPlayer(shooter);

        if(freeFightPlayer == null || enemyFightPlayer == null) {
            return;
        }

        FreeFightSession session1 = SessionManager.getSession(freeFightPlayer);
        FreeFightSession session2 = SessionManager.getSession(enemyFightPlayer);

        if(session1 == null || session2 == null) {
            return;
        }

        e.setCancelled(!session1.equals(session2));

    }

}
