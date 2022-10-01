package kr.teamcocoa.freefight.player;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Kits {

    ONLYSWORD(0), SHIELD(1), DIAMOND_POT(2);

    private int i;

    public static String getNameByEnum(Kits kit) {
        return switch (kit) {
            case ONLYSWORD -> "OnlySword";
            case SHIELD -> "Shield";
            case DIAMOND_POT -> "Diamond Pot";
            default -> "Error : Invalid Kit";
        };
    }

    public static Kits getKitByInt(int i) {
        return switch (i) {
            case 0 -> ONLYSWORD;
            case 1 -> SHIELD;
            case 2 -> DIAMOND_POT;
            default -> throw new IllegalArgumentException("The parameter should be between 0 and 2!");
        };
    }

}
