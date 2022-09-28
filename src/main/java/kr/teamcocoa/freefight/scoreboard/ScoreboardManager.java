package kr.teamcocoa.freefight.scoreboard;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.utils.PlayerUtils;
import kr.teamcocoa.freefight.utils.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScoreboardManager {

    public static void setScoreboard(Player player, List<String> lines) {
        Scoreboard scoreboard = new Scoreboard();
        Objective objective = scoreboard.addObjective("FreeFightSB",
                ObjectiveCriteria.DUMMY,
                Component.Serializer.fromJson("{\n" +
                        "  \"text\": \"" + StringUtils.color("&dFreeFight") + "\"\n" +
                        "}"),
                ObjectiveCriteria.RenderType.INTEGER);

        ClientboundSetObjectivePacket removeObjective = new ClientboundSetObjectivePacket(objective, 1);

        ClientboundSetObjectivePacket createObjective = new ClientboundSetObjectivePacket(objective, 0);

        ClientboundSetDisplayObjectivePacket displayObjective = new ClientboundSetDisplayObjectivePacket(1, objective);

        List<ClientboundSetScorePacket> scores = new ArrayList<>();
        int voidCount = 0;
        int fixedIndex = lines.size() - 1;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals("")) {
                voidCount++;
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < voidCount; j++) {
                    sb.append(" ");
                }
                scores.add(getScorePacket(objective, sb.toString(), fixedIndex));
            }
            else {
                scores.add(getScorePacket(objective, lines.get(i), fixedIndex));
            }
            fixedIndex--;
        }

        PlayerUtils.sendPackets(player, removeObjective, createObjective, displayObjective);
        for (ClientboundSetScorePacket packets : scores) {
            PlayerUtils.sendPackets(player, packets);
        }

    }

    private static ClientboundSetScorePacket getScorePacket(Objective objective, String display, int scoreValue) {
        return new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, objective.getName(), StringUtils.color(display), scoreValue);
    }

    public static void sendScoreboard(Player player) {
        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);

        if(freeFightPlayer == null) {
            return;
        }

        List<String> lines = new LinkedList<>();
        lines.add("&aMcPvP.kr");
        lines.add("");
        lines.add("Kills:");
        lines.add(getArrowMessage(freeFightPlayer.getStats().getKills()) + " ");
        lines.add("");
        lines.add("Deaths:");
        lines.add(getArrowMessage(freeFightPlayer.getStats().getDeaths()) + "  ");
        lines.add("");
        lines.add("KillStreak:");
        lines.add(getArrowMessage(freeFightPlayer.getStats().getKillStreak()) + "   ");

        // getArrowMessage 뒤에 있는 공백들은 제거를 하면 절대 안됨
        // 만약에 저 모든 값들이 0 이라면 보이지 않는 것이 생기기 때문...

        setScoreboard(player, lines);
    }

    private static String getArrowMessage(Object string) {
        return MessageFormat.format("&8» &e{0}", string);
    }

}
