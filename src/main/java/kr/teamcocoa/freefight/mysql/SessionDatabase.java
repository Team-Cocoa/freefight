package kr.teamcocoa.freefight.mysql;

import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.mysql.mysql.MySQL;
import kr.teamcocoa.mysql.mysql.PlaceHolder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionDatabase {

    private static MySQL mysql;

    public static void registerMySQL(MySQL database) {
        if(mysql != null) {
            return;
        }
        mysql = database;
    }

    public static synchronized int registerId(FreeFightSession session) {
        try(    PreparedStatement preparedStatement = mysql.getPreparedStatement("SELECT IFNULL(MAX(id), 0) + 1 AS id FROM sessions;");
                ResultSet resultSet = preparedStatement.executeQuery()) {
            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                PlaceHolder placeHolder = new PlaceHolder(3);
                placeHolder.addPlaceHolder(session.getKits().getI());
                placeHolder.addPlaceHolder(session.getFreeFightPlayer1().getPlayer().getUniqueId().toString());
                placeHolder.addPlaceHolder(session.getFreeFightPlayer2().getPlayer().getUniqueId().toString());
                mysql.update("INSERT INTO sessions(kit, player1, player2, start_time) VALUES(?, ?, ?, UNIX_TIMESTAMP());", placeHolder);
                return id;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void finishGame(int id, UUID winner, UUID loser, byte[] player1Inv, byte[] player2Inv) {
        PlaceHolder placeHolder = new PlaceHolder(5);
        placeHolder.addPlaceHolder(winner.toString());
        placeHolder.addPlaceHolder(loser.toString());
        placeHolder.addPlaceHolder(player1Inv);
        placeHolder.addPlaceHolder(player2Inv);
        placeHolder.addPlaceHolder(id);
        String sql = "UPDATE sessions SET winner = ?, loser = ?, ended = 1, player1_inv = ?, player2_inv = ?, end_time = UNIX_TIMESTAMP() WHERE id = ?";
        mysql.update(sql, placeHolder);
    }





}
