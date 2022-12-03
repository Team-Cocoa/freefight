package kr.teamcocoa.freefight.items.lobby;

import kr.teamcocoa.freefight.items.AbstractItem;
import kr.teamcocoa.freefight.items.ClickAble;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.player.FreeFightPlayerManager;
import kr.teamcocoa.freefight.player.GameState;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import kr.teamcocoa.freefight.utils.ItemUtils;
import kr.teamcocoa.freefight.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpectateItem extends AbstractItem implements ClickAble {

    private static SpectateItem instance;

    public static SpectateItem getInstance() {
        if(instance == null) {
            instance = new SpectateItem();
        }
        return instance;
    }

    private SpectateItem() {
        super(Material.COMPASS);
    }

    @Override
    public ItemStack toItemStack(Player player) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemUtils.name(itemStack, "&6&lSpectator");
        return itemStack;
    }

    @Override
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        FreeFightPlayer freeFightPlayer = FreeFightPlayerManager.getPlayer(player);
        if(freeFightPlayer == null) {
            return;
        }

        if(freeFightPlayer.getState() == GameState.INGAME) {
            return;
        }

        // 만약 상태가 로비라면?
        if(freeFightPlayer.getState() == GameState.LOBBY) {
            // 관전모드로 변경
            freeFightPlayer.setState(GameState.SPECTATE);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false));
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                player.showPlayer(FreeFight.getInstance(), onlinePlayer);
            }
            player.sendMessage(StringUtils.color(
                    FreeFight.getPrefix() + "&aYou are spectator now."
            ));
        }
        else {
            // 아니라면 로비 모드로 바꾸고 기존 로비 위치로 TP + 아이템 지급
            freeFightPlayer.setState(GameState.LOBBY);
            player.setAllowFlight(false);
            player.setFlying(false);
            freeFightPlayer.moveToSpawn();
            for (FreeFightSession session : SessionManager.getSessions()) {
                player.hidePlayer(FreeFight.getInstance(), session.getFreeFightPlayer1().getPlayer());
                player.hidePlayer(FreeFight.getInstance(), session.getFreeFightPlayer2().getPlayer());
            }
            player.sendMessage(StringUtils.color(
                    FreeFight.getPrefix() + "&cYou are no longer spectator now."
            ));
       }
    }
}
