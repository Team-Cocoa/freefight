package kr.teamcocoa.freefight.session.result;

import kr.teamcocoa.freefight.retrofit.head.MojangResultController;
import kr.teamcocoa.freefight.utils.HeadUtils;
import kr.teamcocoa.freefight.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

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
        Utils.catchSynchronous();
        if(!HeadUtils.getHeadValueCache().containsKey(uuid)) {
            try {
                MojangResultController.sendRequest(uuid).get();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return HeadUtils.getHeadFromUUID(uuid);
    }

}
