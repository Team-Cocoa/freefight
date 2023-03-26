package kr.teamcocoa.freefight.kits;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Kits {

    ONLYSWORD(0), SHIELD(1), DIAMOND_POT(2), NETHERITE_POT(3);

    private int i;

    public static String getNameByEnum(Kits kit) {
        return switch (kit) {
            case ONLYSWORD -> "OnlySword";
            case SHIELD -> "Shield";
            case DIAMOND_POT -> "Diamond Pot";
            case NETHERITE_POT -> "Netherite Pot";
            default -> "Error : Invalid Kit";
        };
    }

    public static String getShortNameByEnum(Kits kit) {
        return switch (kit) {
            case ONLYSWORD -> "Sword";
            case SHIELD -> "Shield";
            case DIAMOND_POT -> "DPot";
            case NETHERITE_POT -> "NPot";
            default -> "";
        };
    }

    public static Kits getKitByInt(int i) {
        return switch (i) {
            case 0 -> ONLYSWORD;
            case 1 -> SHIELD;
            case 2 -> DIAMOND_POT;
            case 3 -> NETHERITE_POT;
            default -> throw new IllegalArgumentException("The parameter should be between 0 and 3!");
        };
    }

    public static void makePlayerKit(Player player, Kits kit) {
        switch (kit) {
            case ONLYSWORD -> OnlySwordKit.getInstance().givePlayerKit(player);
            case SHIELD -> ShieldPvPKit.getInstance().givePlayerKit(player);
            case DIAMOND_POT -> DiamondPotKit.getInstance().givePlayerKit(player);
            case NETHERITE_POT -> NetheritePotKit.getInstance().givePlayerKit(player);
        }
    }

}
