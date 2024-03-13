package kr.teamcocoa.freefight.kits;

import dev.derklaro.aerogel.Inject;
import kr.teamcocoa.kitmanager.frontend.kits.LokaPot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Kits {

    ONLYSWORD(0), SHIELD(1), DIAMOND_POT(2), NETHERITE_POT(3), LOKA_POT(4);

    private int i;

    @Inject
    private static OnlySwordKit onlySwordKit;

    @Inject
    private static ShieldPvPKit shieldPvPKit;

    @Inject
    private static DiamondPotKit diamondPotKit;

    @Inject
    private static NetheritePotKit netheritePotKit;

    @Inject
    private static LokaPotKit lokaPotKit;

    public static String getNameByEnum(Kits kit) {
        return switch (kit) {
            case ONLYSWORD -> "OnlySword";
            case SHIELD -> "Shield";
            case DIAMOND_POT -> "Diamond Pot";
            case NETHERITE_POT -> "Netherite Pot";
            case LOKA_POT -> "Loka Pot";
            default -> "Error : Invalid Kit";
        };
    }

    public static String getShortNameByEnum(Kits kit) {
        return switch (kit) {
            case ONLYSWORD -> "Sword";
            case SHIELD -> "Shield";
            case DIAMOND_POT -> "DPot";
            case NETHERITE_POT -> "NPot";
            case LOKA_POT -> "Loka";
            default -> "";
        };
    }

    public static Kits getKitByInt(int i) {
        return switch (i) {
            case 0 -> ONLYSWORD;
            case 1 -> SHIELD;
            case 2 -> DIAMOND_POT;
            case 3 -> NETHERITE_POT;
            case 4 -> LOKA_POT;
            default -> throw new IllegalArgumentException("The parameter should be between 0 and 3!");
        };
    }

    public static void makePlayerKit(Player player, Kits kit) {
        switch (kit) {
            case ONLYSWORD -> onlySwordKit.givePlayerKit(player);
            case SHIELD -> shieldPvPKit.givePlayerKit(player);
            case DIAMOND_POT -> diamondPotKit.givePlayerKit(player);
            case NETHERITE_POT -> netheritePotKit.givePlayerKit(player);
            case LOKA_POT -> lokaPotKit.givePlayerKit(player);
        }
    }

}
