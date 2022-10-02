package kr.teamcocoa.freefight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemUtils {

    public static ItemStack name(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(StringUtils.color(name)));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack enchant(ItemStack itemStack, List<Pair<Enchantment, Integer>> enchantments) {
        for (Pair<Enchantment, Integer> enchantment : enchantments) {
            itemStack.addEnchantment(enchantment.getFirst(), enchantment.getSecond());
        }
        return itemStack;
    }

}
