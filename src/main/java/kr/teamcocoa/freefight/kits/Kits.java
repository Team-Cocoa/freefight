package kr.teamcocoa.freefight.kits;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

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

    public static void makePlayerKit(Player player, Kits kit) {
        switch (kit) {
            case ONLYSWORD -> OnlySwordKit.getInstance().givePlayerKit(player);
            case SHIELD -> ShieldPvPKit.getInstance().givePlayerKit(player);
            case DIAMOND_POT -> DiamondPotKit.getInstance().givePlayerKit(player);
        }
    }

}
