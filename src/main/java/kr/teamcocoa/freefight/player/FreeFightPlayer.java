package kr.teamcocoa.freefight.player;

import kr.teamcocoa.freefight.items.ChallengeItem;
import kr.teamcocoa.freefight.main.FreeFight;
import kr.teamcocoa.freefight.scoreboard.ScoreboardManager;
import kr.teamcocoa.freefight.session.FreeFightSession;
import kr.teamcocoa.freefight.session.SessionManager;
import kr.teamcocoa.freefight.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Getter
public class FreeFightPlayer {

    private Player player;

    @Setter
    private GameState state;

    @Setter
    private Kits currentKit;

    private Stats stats;

    @Setter
    private boolean challengeAble;

    // Thread safe 한 LinkedList 가 없어서 어거지라도 이거 써야지 :sadblob:
    private LinkedBlockingQueue<FreeFightPlayer> challengedPlayerList;

    protected FreeFightPlayer(Player player) {
        this.player = player;
        this.state = GameState.LOBBY;
        this.currentKit = Kits.ONLYSWORD;
        this.challengedPlayerList = new LinkedBlockingQueue<>();
        this.challengeAble = true;

        this.stats = new Stats(player.getUniqueId());
    }

    public void join() {
        // 동기 실행할 몇몇 코드들
        moveToSpawn();
        setInventory(GameState.LOBBY);
        Executors.newSingleThreadExecutor().execute(() -> {
            // 비동기 실행할 몇몇 코드들
            stats.loadStats();
        });

        Bukkit.getScheduler().runTaskTimerAsynchronously(FreeFight.getInstance(), () -> ScoreboardManager.sendScoreboard(player), 0L, 20L);

    }

    public void quit() {
        // 동기 실행할 몇몇 코드들

        Executors.newSingleThreadExecutor().execute(() -> {
            stats.saveStats();
        });
    }

    public void setInventory(GameState state) {
        switch (state) {
            case LOBBY -> {
                ItemStack challengeItem = new ChallengeItem().toItemStack(player);
                Bukkit.getScheduler().runTask(FreeFight.getInstance(), () -> {
                   player.getInventory().clear();
                   player.getInventory().setItem(0, challengeItem);
                });
            }
            case INGAME -> {
                setInGameKit();
            }
        }
    }

    private void setInGameKit() {
        switch (currentKit) {
            case ONLYSWORD -> {
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
                    player.getInventory().setItem(0, sword);
                });
            }
            case SHIELD -> {

            }
        }
    }

    public void moveToSpawn() {
        Location location = new Location(Bukkit.getWorld("TestFreeFight"), 0, 101, 0);
        player.teleport(location);
    }

    public void death() {
        stats.addDeaths();
    }

    public void kill() {
        stats.addKills();
    }

    public void challenge(FreeFightPlayer enemyFightPlayer) {

        if(!challengeAble) {
            return;
        }

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
            for (FreeFightPlayer freeFightPlayer : FreeFightPlayerManager.getPlayerTable().values()) {
                freeFightPlayer.getChallengedPlayerList().remove(this);
                freeFightPlayer.getChallengedPlayerList().remove(enemyFightPlayer);
            }
            SessionManager.addSession(this, enemyFightPlayer, this.currentKit);
            FreeFightSession session = SessionManager.getSession(this);
            session.start();
            return;
        }

        challengedPlayerList.add(enemyFightPlayer);
        player.sendMessage(Component.text(StringUtils.color(
                FreeFight.getPrefix() + "&aYou challenged to &e" + enemyFightPlayer.getPlayer().getName())));
        enemyFightPlayer.getPlayer().sendMessage(Component.text(StringUtils.color(
                FreeFight.getPrefix() + "&e" + player.getName() + " &ahas challenged you!")));

    }



}
