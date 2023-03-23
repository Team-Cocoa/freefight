package kr.teamcocoa.freefight.retrofit.head;

import kr.teamcocoa.retrofit.CallBackAdapter;
import kr.teamcocoa.retrofit.RetrofitFactory;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.UUID;

public class MojangResultController {

    private static Retrofit retrofit = RetrofitFactory.getRetrofit("https://sessionserver.mojang.com/");

    private static MojangSessionService service = retrofit.create(MojangSessionService.class);

    public static void sendRequest(UUID uuid) {
        CallBackAdapter<MojangSessionResult> callBackAdapter = new CallBackAdapter<>();
        callBackAdapter.setIResponse(new HeadOnResponse(uuid));
        callBackAdapter.setIFailure((call, throwable) -> throwable.printStackTrace());
        Call<MojangSessionResult> call = service.getInfo(uuid);
        call.enqueue(callBackAdapter);
    }

}
