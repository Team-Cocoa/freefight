package kr.teamcocoa.freefight.session.result;

import kr.teamcocoa.core.bukkit.utils.ItemUtils;
import kr.teamcocoa.core.network.controllers.mojang.SessionMojangController;
import kr.teamcocoa.core.network.model.MojangProfile;
import kr.teamcocoa.core.utils.AsyncDetector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor
public class ResultPlayer {

    private UUID uuid;
    private double health;
    private double hunger;
    private double saturation;
    private double damageInComing;
    private double damageOutComing;

    public ItemStack getHeadItemStack() {
        // TODO : 모델에서 컨트롤러 역활을 하는 이 함수를 어떻게 모듈화 할지 고민하기
        // 이 함수는 api 요청을 포함하기 때문에 bukkit 스레드에서
        // 실행되면 안됨. catchSync 로 bukkit 스레드 실행 검사 코드가 꼭 필요함.
        AsyncDetector.catchSynchronous();

        CompletableFuture<MojangProfile> completableFuture = SessionMojangController.searchProfileByUUID(uuid);
        try {
            MojangProfile profile = completableFuture.get(10, TimeUnit.SECONDS);
            return ItemUtils.getCustomHead(profile.getHeadValue());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ItemUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVkMjAzMzBkYTU5YzIwN2Q3ODM1MjgzOGU5MWE0OGVhMWU0MmI0NWE5ODkzMjI2MTQ0YjI1MWZlOWI5ZDUzNSJ9fX0=");

    }

}
