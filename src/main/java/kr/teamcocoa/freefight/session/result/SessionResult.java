package kr.teamcocoa.freefight.session.result;

import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.translation.lores.MatchInfoLore;
import kr.teamcocoa.freefight.translation.lores.PotLeftLore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor
public class SessionResult {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20));

    private ResultPlayer resultPlayer1;
    private ResultPlayer resultPlayer2;

    public void initInventory(Player toSee, Inventory inventory) {
        executor.execute(() -> {

            ItemStack result1Head = resultPlayer1.getHeadItemStack();
            setHeadMeta(toSee, result1Head, resultPlayer1);

            ItemStack result2Head = resultPlayer2.getHeadItemStack();
            setHeadMeta(toSee, result2Head, resultPlayer2);

            inventory.setItem(3, result1Head);
            inventory.setItem(5, result2Head);

            Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> toSee.openInventory(inventory));
        });
    }

    private void setHeadMeta(Player toSee, ItemStack itemStack, ResultPlayer resultPlayer) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        MatchInfoLore matchInfoLore = new MatchInfoLore(resultPlayer);
        List<Component> lore = matchInfoLore.getLoreMessage(toSee);

        if(resultPlayer instanceof PotResultPlayer potResultPlayer) {
            PotLeftLore potLeftLore = new PotLeftLore(potResultPlayer.getLeftPot());
            lore.addAll(potLeftLore.getLoreMessage(toSee));
        }

        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
    }

}
