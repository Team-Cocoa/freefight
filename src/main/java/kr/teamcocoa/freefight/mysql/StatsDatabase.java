package kr.teamcocoa.freefight.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import kr.teamcocoa.core.mysql.MySQL;
import kr.teamcocoa.core.utils.AsyncDetector;
import kr.teamcocoa.freefight.player.FreeFightStat;
import kr.teamcocoa.freefight.storages.FreeFightPlayerStorage;

public class StatsDatabase {

    public static void updateStats(UUID uuid, FreeFightStat stats) {
        AsyncDetector.catchSynchronous();

        String sql = "insert into stats(uuid) values(?) on duplicate key update kills = ?, deaths = ?, killStreak = ?;";
        MySQL mysql = FreeFightDatabase.getMySQL();

        mysql.update(sql,
            uuid.toString(),
            stats.getKills(),
            stats.getDeaths(),
            stats.getKillStreak());
    }

    public static FreeFightStat getStatsByUUID(UUID uuid) {
        AsyncDetector.catchSynchronous();
        String sql = "select kills, deaths, killStreak from `stats` where uuid = ?";
        MySQL mysql = FreeFightDatabase.getMySQL();

        FreeFightStat stats = FreeFightStat.builder()
            .kills(0)
            .deaths(0)
            .killStreak(0)
            .build();

        try(PreparedStatement preparedStatement = mysql.getPreparedStatement(sql, uuid.toString());
            ResultSet rs = preparedStatement.executeQuery()) {
            if(rs.next()) {
                int kills = rs.getInt("kills");
                int deaths = rs.getInt("deaths");
                int killStreak = rs.getInt("killStreak");
                
                stats.setKills(kills);
                stats.setDeaths(deaths);
                stats.setKillStreak(killStreak);
                
                FreeFightPlayerStorage.registerStat(uuid, stats);               
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }
    
}
