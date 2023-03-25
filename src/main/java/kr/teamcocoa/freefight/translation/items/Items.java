package kr.teamcocoa.freefight.translation.items;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Items implements Translatable {

    CHALLENGER("challenger"),
    KIT_SELECT("kit_select"),
    SPECTATE("spectate"),
    PLAYER_HEAD("player_head"),

    ;

    private String node;

    @Override
    public String getNode() {
        return "items." + this.node;
    }
}
