package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Messages implements Translatable {

    KILL_LOG("kill_log"),
    KIT_CHANGE("kit_change"),
    START_SPECTATE("start_spectate"),
    STOP_SPECTATE("stop_spectate"),
    JOIN_PLAYER("join_player"),
    LEAVE_PLAYER("leave_player"),
    DIFFERENT_KIT("different_kit"),
    CHALLENGE("challenge"),
    CHALLENGED("challenged"),
    SESSION_ERROR("session_error"),

    ;

    private String node;

    @Override
    public String getNode() {
        return "messages." + this.node;
    }

}
