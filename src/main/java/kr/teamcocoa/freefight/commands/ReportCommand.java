package kr.teamcocoa.freefight.commands;

import kr.teamcocoa.freefight.report.CachedReport;
import kr.teamcocoa.freefight.report.ReportCache;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ReportCommand implements CommandExecutor {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

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

        // 캐싱된 신고를 불러오는 시도
        // 만약에 불러오지 못한다면 DB에 접근 하여
        // DB에 신고 데이터가 있는지 확인하기
        CachedReport cachedReport = ReportCache.getReport(id);

        // 만약 캐싱된 신고가 있다면
        if(cachedReport != null) {

        }
        // 없다면
        else {
            // DB 접근 시작
        }






        return true;
    }
}
