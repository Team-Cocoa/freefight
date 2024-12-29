package kr.teamcocoa.freefight.commands;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kr.teamcocoa.core.utils.StringUtils;
import kr.teamcocoa.core.utils.ThreadUtils;
import kr.teamcocoa.freefight.gui.MatchCheckGUI;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.mysql.SessionDatabase;
import kr.teamcocoa.freefight.session.SessionResult;
import kr.teamcocoa.freefight.storages.SessionResultStorage;
import kr.teamcocoa.freefight.translation.messages.InvalidIdMessage;

public class CheckMatchCommand implements CommandExecutor {

    private static HashMap<UUID, Long> lastCommandExecutedMap = new HashMap<>();

    private static ThreadPoolExecutor executor = ThreadUtils.getThreadPool(20, 1, TimeUnit.SECONDS, "freefight-checkmatchcommand");

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            return true;
        }
        Player player = ((Player) commandSender);

        if(strings.length < 1) {
            player.sendMessage(StringUtils.color(FreeFight.getPrefix() + "&cInvalid parameters."));
            return true;
        }

        String stringId = strings[0].replaceAll("[^0-9]", "");
        if(stringId.equalsIgnoreCase("")) {
            player.sendMessage(StringUtils.color(FreeFight.getPrefix() + "&cInvalid parameters."));
            return true;
        }

        long currentTime = System.currentTimeMillis();

        if(currentTime - lastCommandExecutedMap.getOrDefault(player.getUniqueId(), 0L) < TimeUnit.SECONDS.toMillis(5)) {
            player.sendMessage(StringUtils.color(
                    FreeFight.getPrefix() + "&cHold on! You can use this command every 5 seconds!"));
            return true;
        }

        int id = Integer.parseInt(stringId);

        executor.execute(() -> {
            // 캐싱된 result 확인
            SessionResult result = SessionResultStorage.getResultById(id);
            if(result == null) {
                // db 에서 result 부르기
                result = SessionDatabase.getResult(id);

                // 그럼에도 불구하고 result 가 null 이라면?
                // 그냥 존재하지 않는 아이디
                if(result == null) {
                    player.sendMessage(InvalidIdMessage.getInstance().getMessage(player));
                    return;
                }
            }
            MatchCheckGUI gui = new MatchCheckGUI(result);
            gui.openInventory(player);
        });

        lastCommandExecutedMap.put(player.getUniqueId(), currentTime);

        return true;
    }
}
