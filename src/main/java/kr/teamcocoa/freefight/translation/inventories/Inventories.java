package kr.teamcocoa.freefight.translation.inventories;

import kr.teamcocoa.freefight.translation.Translatable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Inventories implements Translatable {

    KIT_SELECT("kit_select"),
    RESULT("result"),
    SETTING("setting")
    ;

    private String node;

    @Override
    public String getNode() {
        return "inventories." + this.node;
    }
}
