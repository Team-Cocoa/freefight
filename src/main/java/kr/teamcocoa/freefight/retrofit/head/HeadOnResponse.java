package kr.teamcocoa.freefight.retrofit.head;

import com.google.gson.JsonObject;
import kr.teamcocoa.freefight.utils.HeadUtils;
import kr.teamcocoa.freefight.utils.JSONUtils;
import kr.teamcocoa.freefight.utils.Utils;
import kr.teamcocoa.retrofit.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor
public class HeadOnResponse implements IResponse<MojangSessionResult> {

    private UUID uuid;
    private CompletableFuture<Void> completableFuture;

    @Override
    public void onResponse(Call<MojangSessionResult> call, Response<MojangSessionResult> response) {
        if(response.isSuccessful()) {
            MojangSessionResult result = response.body();
            JsonObject property = result.getProperties().get(0).getAsJsonObject();
            String value = property.get("value").getAsString();

            HeadUtils.getHeadValueCache().put(uuid, value);
            HeadUtils.getNameCache().put(uuid, result.getName());

            completableFuture.complete(null);
        }
        else {
            throw new IllegalStateException("Head API Failed! status : " + response.code());
        }
    }
}
