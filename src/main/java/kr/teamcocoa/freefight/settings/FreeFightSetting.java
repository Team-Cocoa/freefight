package kr.teamcocoa.freefight.settings;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FreeFightSetting {

    public final static FreeFightSetting DEFAULT = new FreeFightSetting(false, true);

    private boolean hideArmor = false;

    private boolean displaySessionPlayers = true;

    

}
