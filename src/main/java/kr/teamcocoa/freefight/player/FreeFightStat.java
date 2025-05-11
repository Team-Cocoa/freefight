package kr.teamcocoa.freefight.player;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FreeFightStat {

    private int kills;
    private int deaths;
    private int killStreak;

    public void addKills() {
        kills++;
        killStreak++;
    }

    public void addDeaths() {
        deaths++;
        killStreak = 0;
    }

}
