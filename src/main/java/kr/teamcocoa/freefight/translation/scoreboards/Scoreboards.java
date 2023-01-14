package kr.teamcocoa.freefight.translation.scoreboards;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Scoreboards implements Translatable {

    KILLS("kills"),
    DEATHS("deaths"),
    KILL_STREAK("kill_streak")

    ;

    private String node;

    @Override
    public String getNode() {
        return "scoreboards." + this.node;
    }

}
