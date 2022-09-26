package kr.teamcocoa.freefight.items;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractItem {

    @Getter(AccessLevel.PROTECTED)
    private Material material;

    protected AbstractItem(Material material) {
        this.material = material;
    }

    public abstract ItemStack toItemStack(Player player);
}
