package kr.teamcocoa.freefight.commands;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ForceTPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender.hasPermission("*")) {
            boolean enabled = !FreeFight.isForceTPMode();
            FreeFight.setForceTPMode(enabled);
            commandSender.sendMessage(FreeFight.getPrefix() + StringUtils.color("&eForce TP Mode is " + (enabled ? "&aEnabled" : "&cDisabled") + "&e."));
        }
        else {
            commandSender.sendMessage(StringUtils.color("&a[&dTeamCocoa&a] &7This command does not exist or is deactivated."));
        }
        return true;
    }
}
