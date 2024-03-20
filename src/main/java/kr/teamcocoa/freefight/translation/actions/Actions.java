package kr.teamcocoa.freefight.translation.actions;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Actions implements Translatable {

    REMAIN_TIME("remain_time"),
    SPECTATING("spectating");

    private String node;

    @Override
    public String getNode() {
        return "actions." + node;
    }
}
