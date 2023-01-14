package kr.teamcocoa.freefight.translation.messages;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Messages implements Translatable {

    KILL_LOG("kill_log"),

    ;

    private String node;

    @Override
    public String getNode() {
        return "messages." + this.node;
    }

}
