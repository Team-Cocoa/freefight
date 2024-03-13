package kr.teamcocoa.freefight.mysql;

import kr.teamcocoa.freefight.settings.FreeFightSetting;
import kr.teamcocoa.mysql.mysql.MySQL;
import kr.teamcocoa.mysql.mysql.PlaceHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SettingDatabase {

    public static void upsertSettings(UUID uuid, FreeFightSetting settings) {
        String sql = "INSERT INTO settings(uuid, hide_armor) VALUES(?, ?) ON DUPLICATE KEY UPDATE hide_armor = ?;";
        MySQL mysql = FreeFightDatabase.getMySQL();

        PlaceHolder placeHolder = new PlaceHolder(3);
        placeHolder.addPlaceHolder(uuid.toString());
        placeHolder.addPlaceHolder(settings.isHideArmor() ? 1 : 0);
        placeHolder.addPlaceHolder(settings.isHideArmor() ? 1 : 0);

        mysql.update(sql, placeHolder);
    }

    public static FreeFightSetting getSettings(UUID uuid) {
        String sql = "SELECT * FROM settings WHERE uuid = ?";
        MySQL mysql = FreeFightDatabase.getMySQL();

        try (   PreparedStatement preparedStatement = mysql.getPreparedStatement(sql, uuid.toString());
                ResultSet rs = preparedStatement.executeQuery()) {
            if(rs.next()) {
                boolean hideArmor = rs.getBoolean("hide_armor");

                // TODO : 컬럼들 추가되면 여기다가 추가

                return FreeFightSetting.builder()
                        .hideArmor(hideArmor)
                        .build();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
