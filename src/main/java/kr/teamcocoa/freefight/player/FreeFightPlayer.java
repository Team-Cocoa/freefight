package kr.teamcocoa.freefight.player;

import kr.teamcocoa.freefight.items.ChallengeItem;
import kr.teamcocoa.freefight.main.FreeFight;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.LinkedBlockingQueue;

@Getter
public class FreeFightPlayer {

    private Player player;

    @Setter
    private GameState state;

    @Setter
    private Kits currentKit;

    // Thread safe 한 LinkedList 가 없어서 어거지라도 이거 써야지 :sadblob:
    private LinkedBlockingQueue<FreeFightPlayer> challengedPlayerList;

    protected FreeFightPlayer(Player player) {
        this.player = player;
        this.state = GameState.LOBBY;
        this.currentKit = Kits.ONLYSWORD;
        this.challengedPlayerList = new LinkedBlockingQueue<>();
    }

    public void setInventory(GameState state) {
        switch (state) {
            case LOBBY -> {
                ItemStack challengeItem = new ChallengeItem().toItemStack(player);
                Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
                   player.getInventory().clear();
                   player.getInventory().setItemInMainHand(challengeItem);
                });
            }
            case INGAME -> {
                ItemStack[] armorContent = new ItemStack[4];
                armorContent[3] = new ItemStack(Material.DIAMOND_HELMET);
                armorContent[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
                armorContent[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
                armorContent[0] = new ItemStack(Material.DIAMOND_BOOTS);
                for (ItemStack itemStack : armorContent) {
                    itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                }
                ItemStack sword = new ItemStack(Material.STONE_SWORD);
                sword.addEnchantment(Enchantment.DURABILITY, 3);
                Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
                    player.getInventory().clear();
                    player.getInventory().setArmorContents(armorContent);
                    player.getInventory().setItemInMainHand(sword);
                });
            }
        }
    }

    public void moveToSpawn() {

    }

    public void death() {

    }

    public void challenge(FreeFightPlayer enemyFightPlayer) {

        // 내 state 가 LOBBY 인가?
        // 만약 INGAME 이거나 SPECTATE 면 챌린지를 할 수 없음
        if(state != GameState.LOBBY) {
            return;
        }

        // 상대도 똑같이!
        if(enemyFightPlayer.getState() != GameState.LOBBY) {
            return;
        }

        // 이전에 듀얼을 걸었는지?
        if(challengedPlayerList.contains(enemyFightPlayer)) {
            return;
        }

        // 만약에 상대는 이미 나한테 듀얼을 건 적이 있는지?
        if(enemyFightPlayer.getChallengedPlayerList().contains(this)) {
            // TODO : 게임 스타트!
            return;
        }

        challengedPlayerList.add(enemyFightPlayer);
        // TODO : 이 인스턴스의 Player 에게 듀얼을 걸었다는 메시지 보내기
        // TODO : enemyFightPlayer 에게 이 인스턴스의 Player 로 부터 듀얼을 받았다는 메시지 보내기

    }



}
