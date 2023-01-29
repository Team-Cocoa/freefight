package kr.teamcocoa.freefight.replay;

import kr.teamcocoa.freefight.session.FreeFightSession;
import lombok.Getter;
import me.jumper251.replay.api.ReplayAPI;
import me.jumper251.replay.replaysystem.Replay;
import me.jumper251.replay.replaysystem.data.ActionData;
import me.jumper251.replay.replaysystem.data.ActionType;
import me.jumper251.replay.replaysystem.data.types.ChatData;
import me.jumper251.replay.replaysystem.data.types.LocationData;
import me.jumper251.replay.replaysystem.data.types.WorldChangeData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
public class SessionReplay {

    private FreeFightSession session;

    private Replay replay;

    private String id;

    public SessionReplay(FreeFightSession session) {
        this.session = session;
        this.id = "ff_" + session.getId();
    }

    public void startReplay() {
        List<Player> playerList = new ArrayList<>(2);

        playerList.add(session.getFreeFightPlayer1().getPlayer());
        playerList.add(session.getFreeFightPlayer2().getPlayer());

        this.replay = ReplayAPI.getInstance().recordReplay(
                id,
                Bukkit.getConsoleSender(),
                playerList);

        replay.getRecorder().addData(0, new ActionData(0,
                ActionType.CUSTOM,
                "worldChangeData",
                new WorldChangeData(new LocationData(0, 50, 0, "TestFreeFight"))));

        SessionReplayManager.register(id, this);

    }

    public void stopReplay() {
        replay.setId(id);
        ReplayAPI.getInstance().stopReplay(id, true);

        Executors.newSingleThreadScheduledExecutor().schedule(() -> SessionReplayManager.unregister(id), 5, TimeUnit.MINUTES);
    }

    public void addMessage(LogType logType, String message) {
        replay.getRecorder().addData(replay.getRecorder().getCurrentTick(), new ActionData(replay.getRecorder().getCurrentTick(),
                ActionType.MESSAGE,
                logType.getDataName(),
                new ChatData(message)));
    }

}
