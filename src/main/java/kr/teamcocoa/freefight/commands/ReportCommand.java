package kr.teamcocoa.freefight.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) {
            return false;
        }

        if(strings.length < 1) {
            commandSender.sendMessage("Invalid parameter.");
            return true;
        }

        Player player = ((Player) commandSender);

        String id = strings[0];






        return true;
    }
}
