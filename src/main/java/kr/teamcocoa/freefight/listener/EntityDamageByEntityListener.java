package kr.teamcocoa.freefight.listener;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemFactory;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
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
    }

    private void challengerHandle(EntityDamageByEntityEvent e) {
        Player player = (Player) e.getEntity();
        Player enemy = (Player) e.getDamager();

        ItemStack mainHandItem = enemy.getInventory().getItemInMainHand();

        if(mainHandItem.hasItemMeta()
                && mainHandItem.getItemMeta().hasDisplayName()
                && mainHandItem.getItemMeta().getDisplayName().equals(StringUtils.color("&6&lChallenger"))) {
            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
            FreeFightPlayer enemyFreeFightPlayer = FreeFightPlayerManager.getPlayer(enemy);

            enemyFreeFightPlayer.challenge(freeFightPlayer);
        }
    }

}
