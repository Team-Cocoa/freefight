package kr.teamcocoa.freefight.items.lobby;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import kr.teamcocoa.core.bukkit.utils.ComponentUtils;
import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.items.ClickAble;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.translation.items.SpectateTitle;
import kr.teamcocoa.freefight.translation.messages.StartSpectateMessage;
import kr.teamcocoa.freefight.translation.messages.StopSpectateMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedList;
import java.util.List;

@Singleton
public class SpectateItem extends AbstractItem implements ClickAble {

    private List<FreeFightPlayer> delayList;

    private SpectateTitle spectateTitle;
    private StartSpectateMessage startSpectateMessage;
    private StopSpectateMessage stopSpectateMessage;

    @Inject
    private SpectateItem(
            SpectateTitle spectateTitle,
            StartSpectateMessage startSpectateMessage,
            StopSpectateMessage stopSpectateMessage
    ) {
        super(Material.COMPASS);
        this.delayList = new LinkedList<>();
        this.spectateTitle = spectateTitle;
        this.startSpectateMessage = startSpectateMessage;
        this.stopSpectateMessage = stopSpectateMessage;
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, spectateTitle.getMessage(player));
        return itemStack;
    }

    @Override
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
        if (freeFightPlayer == null) {
            return;
        }

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        // NPE 방지
        if(!itemInMainHand.hasItemMeta() || !itemInMainHand.getItemMeta().hasDisplayName()) {
            return;
        }

        if(!ComponentUtils.componentEquals(itemInMainHand.getItemMeta().displayName(), spectateTitle.getMessage(player))) {
            return;
        }

        if (freeFightPlayer.getState() == GameState.INGAME) {
            return;
        }

        if (delayList.contains(freeFightPlayer)) {
            return;
        }

        // 만약 상태가 로비라면?
        if (freeFightPlayer.getState() == GameState.LOBBY) {
            // 관전모드로 변경
            freeFightPlayer.setState(GameState.SPECTATE);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false));
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                player.showPlayer(FreeFight.getInstance(), onlinePlayer);
            }
            player.sendMessage(startSpectateMessage.getMessage(player));
        }
        else {
            // 아니라면 로비 모드로 바꾸고 기존 로비 위치로 TP + 아이템 지급
            freeFightPlayer.setState(GameState.LOBBY);
            freeFightPlayer.resetPlayer();
            for (FreeFightPlayer fightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
                if(fightPlayer.getState() == GameState.INGAME) {
                    player.hidePlayer(FreeFight.getInstance(), fightPlayer.getPlayer());
                }
                else {
                    fightPlayer.getPlayer().showPlayer(FreeFight.getInstance(), player);
                }
            }
            player.sendMessage(stopSpectateMessage.getMessage(player));
        }

        delayList.add(freeFightPlayer);
        Bukkit.getScheduler().runTaskLater(FreeFight.getInstance(), () -> delayList.remove(freeFightPlayer), 20L);
    }
}
