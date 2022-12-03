package kr.teamcocoa.freefight.session;

import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.kits.Kits;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionManager {

    private static HashMap<FreeFightPlayer, FreeFightSession> playerTable = new HashMap<>();

    @Getter
    private static Set<FreeFightSession> sessions = new HashSet<>();

    public static boolean addSession(FreeFightPlayer player1, FreeFightPlayer player2, Kits kit) {
        if(player1.getState() != GameState.LOBBY && player2.getState() != GameState.LOBBY) {
            return false;
        }

        if(player1.getCurrentKit() != player2.getCurrentKit()) {
            return false;
        }

        if(player1.getCurrentKit() != kit) {
            return false;
        }

        FreeFightSession session = new FreeFightSession(player1, player2, kit);
        playerTable.put(player1, session);
        playerTable.put(player2, session);
        sessions.add(session);

        return true;
    }

    public static FreeFightSession getSession(FreeFightPlayer player) {
        return playerTable.getOrDefault(player, null);
    }

    public static boolean removeSession(FreeFightSession session) {
        if(!playerTable.containsValue(session)) {
            return false;
        }

        playerTable.remove(session.getFreeFightPlayer1());
        playerTable.remove(session.getFreeFightPlayer2());
        sessions.remove(session);

        return true;
    }



}
