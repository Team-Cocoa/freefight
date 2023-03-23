package kr.teamcocoa.freefight.mysql;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.result.PotResultPlayer;
import kr.teamcocoa.freefight.session.result.ResultPlayer;
import kr.teamcocoa.freefight.session.result.SessionResult;
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

    public static void finishGame(int id, UUID winner, UUID loser, byte[] player1Inv, byte[] player2Inv,
                                  double player1DamageIn, double player1DamageOut, double player2DamageIn, double player2DamageOut,
                                  double player1Health, double player2Health, float player1Saturation, float player2Saturation,
                                  int player1Hunger, int player2Hunger) {
        PlaceHolder placeHolder = new PlaceHolder(15);
        placeHolder.addPlaceHolder(winner.toString());
        placeHolder.addPlaceHolder(loser.toString());
        placeHolder.addPlaceHolder(player1Inv);
        placeHolder.addPlaceHolder(player1Health);
        placeHolder.addPlaceHolder(player1DamageIn);
        placeHolder.addPlaceHolder(player1DamageOut);
        placeHolder.addPlaceHolder(player1Saturation);
        placeHolder.addPlaceHolder(player1Hunger);
        placeHolder.addPlaceHolder(player2Inv);
        placeHolder.addPlaceHolder(player2Health);
        placeHolder.addPlaceHolder(player2DamageIn);
        placeHolder.addPlaceHolder(player2DamageOut);
        placeHolder.addPlaceHolder(player2Saturation);
        placeHolder.addPlaceHolder(player2Hunger);
        placeHolder.addPlaceHolder(id);
        String sql = "UPDATE sessions SET winner = ?, loser = ?, ended = 1, " +
                "player1_inv = ?, player1_health = ?, player1_damage_in = ?, player1_damage_out = ?, player1_saturation = ?, player1_hunger = ?, " +
                "player2_inv = ?, player2_health = ?, player2_damage_in = ?, player2_damage_out = ?, player2_saturation = ?, player2_hunger = ?, " +
                "end_time = UNIX_TIMESTAMP() WHERE id = ?";
        mysql.update(sql, placeHolder);
    }

    public static SessionResult getResult(int id) {
        String sql = "SELECT * FROM sessions WHERE id = ?";
        try(    PreparedStatement preparedStatement = mysql.getPreparedStatement(sql, id);
                ResultSet rs = preparedStatement.executeQuery()) {
            if(rs.next()) {

                Kits kit = Kits.getKitByInt(rs.getInt("kit"));

                UUID player1UUID = UUID.fromString(rs.getString("player1"));
                double player1Health = rs.getDouble("player1_health");
                double player1DamageIn = rs.getDouble("player1_damage_in");
                double player1DamageOut = rs.getDouble("player1_damage_out");
                double player1Saturation = rs.getDouble("player1_saturation");
                double player1Hunger = rs.getDouble("player1_hunger");

                ResultPlayer resultPlayer1;

                if(kit == Kits.DIAMOND_POT) {
                    // TODO : 팟 남은 개수 구하는 로직 추가
                    resultPlayer1 = new PotResultPlayer(
                            player1UUID,
                            player1Health,
                            player1Hunger,
                            player1Saturation,
                            player1DamageIn,
                            player1DamageOut,
                            0);
                }
                else {
                    resultPlayer1 = new ResultPlayer(
                            player1UUID,
                            player1Health,
                            player1Hunger,
                            player1Saturation,
                            player1DamageIn,
                            player1DamageOut);
                }

                UUID player2UUID = UUID.fromString(rs.getString("player2"));
                double player2Health = rs.getDouble("player2_health");
                double player2DamageIn = rs.getDouble("player2_damage_in");
                double player2DamageOut = rs.getDouble("player2_damage_out");
                double player2Saturation = rs.getDouble("player2_saturation");
                double player2Hunger = rs.getDouble("player2_hunger");

                ResultPlayer resultPlayer2;

                if(kit == Kits.DIAMOND_POT) {
                    resultPlayer2 = new PotResultPlayer(
                            player2UUID,
                            player2Health,
                            player2Hunger,
                            player2Saturation,
                            player2DamageIn,
                            player2DamageOut,
                            0);
                }
                else {
                    resultPlayer2 = new ResultPlayer(
                            player2UUID,
                            player2Health,
                            player2Hunger,
                            player2Saturation,
                            player2DamageIn,
                            player2DamageOut);
                }

                SessionResult sessionResult = new SessionResult(resultPlayer1, resultPlayer2);

                // TODO : sessionResult 캐싱 만들기

                return sessionResult;

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
