package kr.teamcocoa.freefight.tab;

import eu.cloudnetservice.driver.event.EventListener;
import eu.cloudnetservice.driver.event.events.permission.PermissionUpdateGroupEvent;
import eu.cloudnetservice.driver.event.events.permission.PermissionUpdateUserEvent;
import eu.cloudnetservice.driver.permission.PermissionManagement;
import eu.cloudnetservice.driver.permission.PermissionUser;
import kr.teamcocoa.freefight.main.FreeFight;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class TabListener implements Listener {

    private PermissionManagement permissionManagement;

    @EventListener
    public void handle(PermissionUpdateUserEvent event) {
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getUniqueId().equals(event.permissionUser().uniqueId()))
                .findFirst()
                .ifPresent(TabManager::updateNameTags));
    }

    @EventListener
    public void handle(PermissionUpdateGroupEvent event) {
        Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> Bukkit.getOnlinePlayers().forEach(player -> {
            PermissionUser permissionUser = permissionManagement.user(player.getUniqueId());

            if (permissionUser != null && permissionUser.inGroup(event.permissionGroup().name())) {
                TabManager.updateNameTags(player);
            }
        }));
    }

}
