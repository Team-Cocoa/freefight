package kr.teamcocoa.freefight.player;

import kr.teamcocoa.freefight.mysql.FreeFightDatabase;
import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Getter
public class Stats {

    private int kills;
    private int deaths;
    private int killStreak;

    private UUID uuid;

    protected Stats(UUID uuid) {
        this.uuid = uuid;
    }

    public void addKills() {
        kills++;
        killStreak++;
    }

    public void addDeaths() {
        deaths++;
        killStreak = 0;
    }

    public void loadStats() {
        try(PreparedStatement preparedStatement = FreeFightDatabase.getMySQL().getPreparedStatement("SELECT * FROM `stats` WHERE uuid = ?", uuid.toString());
            ResultSet rs = preparedStatement.executeQuery()) {
            if(rs.next()) {
                this.kills = rs.getInt("kills");
                this.deaths = rs.getInt("deaths");
                this.killStreak = rs.getInt("killStreak");
            }
            else {
                FreeFightDatabase.getMySQL().update("INSERT INTO stats(uuid) VALUES(?);", uuid.toString());
                this.kills = 0;
                this.deaths = 0;
                this.killStreak = 0;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveStats() {
        FreeFightDatabase.getMySQL().update("UPDATE stats SET kills = ?, deaths = ?, killStreak = ? WHERE uuid = ?", kills, deaths, killStreak, uuid.toString());
    }

}
