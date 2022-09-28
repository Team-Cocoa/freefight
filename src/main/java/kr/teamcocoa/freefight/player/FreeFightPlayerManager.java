package kr.teamcocoa.freefight.player;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FreeFightPlayerManager {

    @Getter
    private static Map<Player, FreeFightPlayer> playerTable = new ConcurrentHashMap<>();

    public static boolean addPlayer(Player player) {
        if(playerTable.containsKey(player)) {
            return false;
        }
        FreeFightPlayer freeFightPlayer = new FreeFightPlayer(player);
        playerTable.put(player, freeFightPlayer);
        return true;
    }

    public static FreeFightPlayer getPlayer(Player player) {
        return playerTable.getOrDefault(player, null);
    }

    public static boolean removePlayer(Player player) {
        if(!playerTable.containsKey(player)) {
            return false;
        }
        playerTable.remove(player);
        return true;
    }

}
