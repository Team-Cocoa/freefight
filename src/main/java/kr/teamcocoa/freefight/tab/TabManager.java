package kr.teamcocoa.freefight.tab;

import com.google.common.base.Preconditions;
import eu.cloudnetservice.driver.permission.PermissionGroup;
import eu.cloudnetservice.driver.permission.PermissionManagement;
import eu.cloudnetservice.driver.permission.PermissionUser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class TabManager {

    public static void updateNameTags(Player player) {
        updateNameTags(player, null);
    }

    public static void updateNameTags(Player player, Function<Player, PermissionGroup> playerIPermissionGroupFunction) {
        updateNameTags(player, playerIPermissionGroupFunction, null);
    }

    public static void updateNameTags(Player player, Function<Player, PermissionGroup> playerIPermissionGroupFunction,
                               Function<Player, PermissionGroup> allOtherPlayerPermissionGroupFunction) {
        PermissionManagement permissionManagement = FreeFight.getPermissionManagement();

        Preconditions.checkNotNull(player);

        PermissionUser playerPermissionUser = permissionManagement.user(player.getUniqueId());
        AtomicReference<PermissionGroup> playerPermissionGroup = new AtomicReference<>(
                playerIPermissionGroupFunction != null ? playerIPermissionGroupFunction.apply(player) : null);

        if (playerPermissionUser != null && playerPermissionGroup.get() == null) {

            playerPermissionGroup
                    .set(permissionManagement.highestPermissionGroup(playerPermissionUser));

            if (playerPermissionGroup.get() == null) {
                playerPermissionGroup.set(permissionManagement.defaultPermissionGroup());
            }
        }

        int sortIdLength = permissionManagement.groups().stream()
                .map(PermissionGroup::sortId)
                .map(String::valueOf)
                .mapToInt(String::length)
                .max()
                .orElse(0);

        initScoreboard(player);

        FreeFightPlayer playerFreeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        Bukkit.getOnlinePlayers().forEach(all -> {

            initScoreboard(all);

            FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(all);

            if (playerPermissionGroup.get() != null) {
                addTeamEntry(player, all, playerPermissionGroup.get(), sortIdLength, playerFreeFightPlayer.getCurrentKit());
            }

            PermissionUser targetPermissionUser = permissionManagement
                    .user(all.getUniqueId());
            PermissionGroup targetPermissionGroup =
                    allOtherPlayerPermissionGroupFunction != null ? allOtherPlayerPermissionGroupFunction.apply(all) : null;

            if (targetPermissionUser != null && targetPermissionGroup == null) {
                targetPermissionGroup = permissionManagement
                        .highestPermissionGroup(targetPermissionUser);

                if (targetPermissionGroup == null) {
                    targetPermissionGroup = permissionManagement.defaultPermissionGroup();
                }
            }

            if (targetPermissionGroup != null) {
                addTeamEntry(all, player, targetPermissionGroup, sortIdLength, freeFightPlayer.getCurrentKit());
            }
        });
    }

    private static void addTeamEntry(Player target, Player all, PermissionGroup permissionGroup, int highestSortIdLength, Kits kits) {
        int sortIdLength = String.valueOf(permissionGroup.sortId()).length();
        String teamName = (
                highestSortIdLength == sortIdLength ?
                        permissionGroup.sortId() :
                        String.format("%0" + highestSortIdLength + "d", permissionGroup.sortId())
        ) + permissionGroup.name() + Kits.getNameByEnum(kits);

        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }

        Team team = all.getScoreboard().getTeam(teamName);
        if (team == null) {
            team = all.getScoreboard().registerNewTeam(teamName);
        }

        String prefix = permissionGroup.prefix();
        String color = permissionGroup.color();
        String suffix = String.format("&8 | &b[%s]", Kits.getShortNameByEnum(kits));

        try {
            Method method = team.getClass().getDeclaredMethod("setColor", ChatColor.class);
            method.setAccessible(true);

            if (color != null && !color.isEmpty()) {
                ChatColor chatColor = ChatColor.getByChar(color.replaceAll("&", "").replaceAll("§", ""));
                if (chatColor != null) {
                    method.invoke(team, chatColor);
                }
            } else {
                color = ChatColor.getLastColors(prefix.replace('&', '§'));
                if (!color.isEmpty()) {
                    ChatColor chatColor = ChatColor.getByChar(color.replaceAll("&", "").replaceAll("§", ""));
                    if (chatColor != null) {
                        FreeFight.getPermissionManagement().updateGroup(PermissionGroup.builder(permissionGroup).color(color).build());
                        method.invoke(team, chatColor);
                    }
                }
            }
        } catch (NoSuchMethodException ignored) {
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }

        team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));

        team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));

        team.addEntry(target.getName());

        target.setDisplayName(ChatColor.translateAlternateColorCodes('&', permissionGroup.display() + target.getName()));

    }

    private static void initScoreboard(Player all) {
        if (all.getScoreboard().equals(all.getServer().getScoreboardManager().getMainScoreboard())) {
            all.setScoreboard(all.getServer().getScoreboardManager().getNewScoreboard());
        }
    }
}
