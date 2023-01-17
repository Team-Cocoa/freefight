package kr.teamcocoa.freefight.tab;

import com.google.common.base.Preconditions;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import kr.teamcocoa.freefight.kits.Kits;
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

    public static void updateNameTags(Player player, Function<Player, IPermissionGroup> playerIPermissionGroupFunction) {
        updateNameTags(player, playerIPermissionGroupFunction, null);
    }

    public static void updateNameTags(Player player, Function<Player, IPermissionGroup> playerIPermissionGroupFunction,
                               Function<Player, IPermissionGroup> allOtherPlayerPermissionGroupFunction) {
        Preconditions.checkNotNull(player);

        IPermissionUser playerPermissionUser = CloudNetDriver.getInstance().getPermissionManagement()
                .getUser(player.getUniqueId());
        AtomicReference<IPermissionGroup> playerPermissionGroup = new AtomicReference<>(
                playerIPermissionGroupFunction != null ? playerIPermissionGroupFunction.apply(player) : null);

        if (playerPermissionUser != null && playerPermissionGroup.get() == null) {
            playerPermissionGroup
                    .set(CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(playerPermissionUser));

            if (playerPermissionGroup.get() == null) {
                playerPermissionGroup.set(CloudNetDriver.getInstance().getPermissionManagement().getDefaultPermissionGroup());
            }
        }

        int sortIdLength = CloudNetDriver.getInstance().getPermissionManagement().getGroups().stream()
                .map(IPermissionGroup::getSortId)
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

            IPermissionUser targetPermissionUser = CloudNetDriver.getInstance().getPermissionManagement()
                    .getUser(all.getUniqueId());
            IPermissionGroup targetPermissionGroup =
                    allOtherPlayerPermissionGroupFunction != null ? allOtherPlayerPermissionGroupFunction.apply(all) : null;

            if (targetPermissionUser != null && targetPermissionGroup == null) {
                targetPermissionGroup = CloudNetDriver.getInstance().getPermissionManagement()
                        .getHighestPermissionGroup(targetPermissionUser);

                if (targetPermissionGroup == null) {
                    targetPermissionGroup = CloudNetDriver.getInstance().getPermissionManagement().getDefaultPermissionGroup();
                }
            }

            if (targetPermissionGroup != null) {
                addTeamEntry(all, player, targetPermissionGroup, sortIdLength, freeFightPlayer.getCurrentKit());
            }
        });
    }

    private static void addTeamEntry(Player target, Player all, IPermissionGroup permissionGroup, int highestSortIdLength, Kits kits) {
        int sortIdLength = String.valueOf(permissionGroup.getSortId()).length();
        String teamName = (
                highestSortIdLength == sortIdLength ?
                        permissionGroup.getSortId() :
                        String.format("%0" + highestSortIdLength + "d", permissionGroup.getSortId())
        ) + permissionGroup.getName() + Kits.getNameByEnum(kits);

        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }

        Team team = all.getScoreboard().getTeam(teamName);
        if (team == null) {
            team = all.getScoreboard().registerNewTeam(teamName);
        }

        String prefix = permissionGroup.getPrefix();
        String color = permissionGroup.getColor();
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
                        permissionGroup.setColor(color);
                        CloudNetDriver.getInstance().getPermissionManagement().updateGroup(permissionGroup);
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

        target.setDisplayName(ChatColor.translateAlternateColorCodes('&', permissionGroup.getDisplay() + target.getName()));

        Bukkit.getLogger().info(MessageFormat.format("{0} s kit : {1} | appliedPlayer : {2}",
                all.getName(), Kits.getNameByEnum(kits), target.getName()));
    }

    private static void initScoreboard(Player all) {
        if (all.getScoreboard().equals(all.getServer().getScoreboardManager().getMainScoreboard())) {
            all.setScoreboard(all.getServer().getScoreboardManager().getNewScoreboard());
        }
    }
}
