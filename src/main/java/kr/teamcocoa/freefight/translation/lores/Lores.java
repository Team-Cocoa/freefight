package kr.teamcocoa.freefight.translation.lores;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Lores implements Translatable {

    MATCH_INFO("match_info"),
    POT_LEFT("pot_left"),
    ;

    private String node;

    @Override
    public String getNode() {
        return "lores." + this.node;
    }
}
