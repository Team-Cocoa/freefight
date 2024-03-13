package kr.teamcocoa.freefight.commands;

import kr.teamcocoa.core.config.MessageConfig;
import kr.teamcocoa.core.permission.PermissionValidator;
import kr.teamcocoa.core.utils.StringUtils;
import kr.teamcocoa.freefight.main.FreeFight;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForceTPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission("*")) {
            boolean enabled = !FreeFight.isForceTPMode();
            FreeFight.setForceTPMode(enabled);
            commandSender.sendMessage(FreeFight.getPrefix() + StringUtils.color("&eForce TP Mode is " + (enabled ? "&aEnabled" : "&cDisabled") + "&e."));
        }
        else {
            commandSender.sendMessage(MessageConfig.NO_PERMISSION);
        }
        return true;
    }
}
