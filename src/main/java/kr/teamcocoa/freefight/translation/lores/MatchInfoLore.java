package kr.teamcocoa.freefight.translation.lores;

import dev.derklaro.aerogel.Inject;
import kr.teamcocoa.core.network.controllers.mojang.SessionMojangController;
import kr.teamcocoa.core.network.model.MojangProfile;
import kr.teamcocoa.core.utils.StringUtils;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.session.result.SessionResult;
import kr.teamcocoa.freefight.translation.BaseMessage;
import kr.teamcocoa.freefight.translation.LoreMessage;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

@Getter
public class MatchInfoLore extends BaseMessage implements LoreMessage {

    private String kit;
    private String winner;
    private String startedTime;
    private String endedTime;

    private Object[] arguments;

    @Inject
    private static NoWinnerLore noWinnerLore;

    public MatchInfoLore(SessionResult sessionResult, Player toSee) {
        super(Lores.MATCH_INFO);
        this.kit = Kits.getNameByEnum(sessionResult.getKit());

        if(sessionResult.getWinner() == null) {
            this.winner = noWinnerLore.getMessage(toSee);
        }
        else {
            MojangProfile profile = SessionMojangController.getUuidToProfileCache().readData(sessionResult.getWinner());
            this.winner = profile == null
                    ? sessionResult.getWinner().toString()
                    : profile.getUserName();
        }

        this.startedTime = StringUtils.getTimestampToDate(sessionResult.getStartTime());
        this.endedTime = StringUtils.getTimestampToDate(sessionResult.getEndTIme());

        this.arguments = new Object[] { kit, winner, startedTime, endedTime };
    }

    @Override
    public String getMessage(Player player) {
        StringBuilder sb = new StringBuilder();

        String[] messages = getArrayMessage(player.getUniqueId());
        for (int i = 0; i < messages.length; i++) {
            String message = messages[i].trim();
            sb.append(message + (i == message.length() - 1 ? "" : "\n"));
        }
        return MessageFormat.format(sb.toString(), arguments);
    }

    @Override
    public List<Component> getLoreMessage(Player player) {
        String[] rawMessages = getMessage(player).split("\n");
        List<Component> list = new LinkedList<>();
        for (String rawMessage : rawMessages) {
            list.add(Component.text(rawMessage));
        }
        return list;
    }

}
