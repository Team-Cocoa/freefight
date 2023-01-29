package kr.teamcocoa.freefight.mysql;

import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.mysql.mysql.MySQL;
import kr.teamcocoa.mysql.mysql.PlaceHolder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                placeHolder.addPlaceHolder(session.getFreeFightPlayer1().getPlayer().getUniqueId());
                placeHolder.addPlaceHolder(session.getFreeFightPlayer2().getPlayer().getUniqueId());
                mysql.update("INSERT INTO sessions(kit, player1, player2, start_time) VALUES(?, ?, ?, UNIX_TIMESTAMP());", placeHolder);
                return id;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }





}
