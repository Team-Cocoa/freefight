package kr.teamcocoa.freefight.kits;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class AbstractKit {

    private Kits kit;

    protected AbstractKit(Kits kit) {
        this.kit = kit;
    }

    public abstract void givePlayerKit(Player player);

}
