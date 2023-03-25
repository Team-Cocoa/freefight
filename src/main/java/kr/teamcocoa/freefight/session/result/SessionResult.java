package kr.teamcocoa.freefight.session.result;

import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.translation.items.PlayerHeadTitle;
import kr.teamcocoa.freefight.translation.lores.PlayerInfoLore;
import kr.teamcocoa.freefight.translation.lores.PotLeftLore;
import kr.teamcocoa.freefight.utils.HeadUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class SessionResult {

    private int id;
    private Kits kit;
    private UUID winner;
    private long startTime;
    private long endTIme;

    private ResultPlayer resultPlayer1;
    private ResultPlayer resultPlayer2;

    public void initInventory(Player toSee, Inventory inventory) {
        ItemStack result1Head = resultPlayer1.getHeadItemStack();
        setHeadMeta(toSee, result1Head, resultPlayer1);

        ItemStack result2Head = resultPlayer2.getHeadItemStack();
        setHeadMeta(toSee, result2Head, resultPlayer2);

        inventory.setItem(2, result1Head);
        inventory.setItem(6, result2Head);
    }

    private void setHeadMeta(Player toSee, ItemStack itemStack, ResultPlayer resultPlayer) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PlayerInfoLore playerInfoLore = new PlayerInfoLore(resultPlayer);
        List<Component> lore = playerInfoLore.getLoreMessage(toSee);

        if(resultPlayer instanceof PotResultPlayer potResultPlayer) {
            PotLeftLore potLeftLore = new PotLeftLore(potResultPlayer.getLeftPot());
            lore.addAll(potLeftLore.getLoreMessage(toSee));
        }

        itemMeta.lore(lore);

        PlayerHeadTitle title = new PlayerHeadTitle(HeadUtils.getNameCache().get(resultPlayer.getUuid()));
        itemMeta.displayName(Component.text(title.getMessage(toSee)));

        itemStack.setItemMeta(itemMeta);
    }

}
