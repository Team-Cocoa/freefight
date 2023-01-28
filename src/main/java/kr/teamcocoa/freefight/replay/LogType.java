package kr.teamcocoa.freefight.replay;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LogType {
    ARENA("ArenaLog"),
    ANTI_CHEAT("ACLog"),
    OTHER("Other");

    private String dataName;
}
