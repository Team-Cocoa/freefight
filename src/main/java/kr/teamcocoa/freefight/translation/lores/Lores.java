package kr.teamcocoa.freefight.translation.lores;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Lores implements Translatable {

    PLAYER_INFO("player_info"),
    POT_LEFT("pot_left"),
    MATCH_INFO("match_info"),
    NO_WINNER("no_winner"),
    SETTING_HIDE_ARMOR("setting_hide_armor"),
    SETTING_DISPLAY_SESSION_PLAYERS("setting_display_session_players")
    ;

    private String node;

    @Override
    public String getNode() {
        return "lores." + this.node;
    }
}
