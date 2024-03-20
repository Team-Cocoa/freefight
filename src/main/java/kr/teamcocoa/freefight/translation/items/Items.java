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
    MATCH_HEAD("match_head"),
    SETTING("setting"),
    SETTING_HIDE_ARMOR("setting_hide_armor"),
    SETTING_DISPLAY_SESSION_PLAYERS("setting_display_session_players"),
    SETTING_ENABLE("setting_enable"),
    SETTING_DISABLE("setting_disable")

    ;

    private String node;

    @Override
    public String getNode() {
        return "items." + this.node;
    }
}
