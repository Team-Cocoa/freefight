package kr.teamcocoa.freefight.translation.titles;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Titles implements Translatable {

    VICTORY("victory"),
    DEFEAT("defeat"),
    START_GAME("start_game"),
    FINISH_GAME("finish_game"),
    ENEMY("enemy"),
    ;

    private String node;

    @Override
    public String getNode() {
        return "titles." + this.node;
    }
}
