package kr.teamcocoa.freefight.kits;

import kr.teamcocoa.kitmanager.frontend.api.KitManagerAPI;
import kr.teamcocoa.kitmanager.frontend.main.KitManager;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class AbstractKit {

    protected static KitManagerAPI kitAPI = KitManager.getKitManagerAPI();

    private Kits kit;

    protected AbstractKit(Kits kit) {
        this.kit = kit;
    }

    public abstract void givePlayerKit(Player player);

}
