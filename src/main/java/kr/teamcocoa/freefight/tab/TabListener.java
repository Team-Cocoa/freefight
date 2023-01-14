package kr.teamcocoa.freefight.tab;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.permission.PermissionUpdateGroupEvent;
import de.dytanic.cloudnet.driver.event.events.permission.PermissionUpdateUserEvent;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import kr.teamcocoa.freefight.main.FreeFight;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class TabListener implements Listener {

    @EventListener
    public void handle(PermissionUpdateUserEvent event) {
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getUniqueId().equals(event.getPermissionUser().getUniqueId()))
                .findFirst()
                .ifPresent(TabManager::updateNameTags));
    }

    @EventListener
    public void handle(PermissionUpdateGroupEvent event) {
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> Bukkit.getOnlinePlayers().forEach(player -> {
            IPermissionUser permissionUser = CloudNetDriver.getInstance().getPermissionManagement()
                    .getUser(player.getUniqueId());

            if (permissionUser != null && permissionUser.inGroup(event.getPermissionGroup().getName())) {
                TabManager.updateNameTags(player);
            }
        }));
    }

}
