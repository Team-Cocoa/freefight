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

@Getter
@AllArgsConstructor
public class HeadOnResponse implements IResponse<MojangSessionResult> {

    private UUID uuid;

    @Override
    public void onResponse(Call<MojangSessionResult> call, Response<MojangSessionResult> response) {
        if(response.isSuccessful()) {
            MojangSessionResult result = response.body();
            Utils.catchSynchronous();
            Utils.catchAsynchronous();
            JsonObject property = result.getProperties().get(0).getAsJsonObject();
            String rawValue = property.get("value").getAsString();
            String decodedValue = new String(Base64.getDecoder().decode(rawValue));
            JsonObject decodedSkinJson = JSONUtils.parse(decodedValue).getAsJsonObject();
            String skinValue =
                    decodedSkinJson.get("textures").getAsJsonObject()
                    .get("SKIN").getAsJsonObject()
                    .get("url").getAsString().substring(37);

            HeadUtils.getHeadValueCache().put(uuid, skinValue);
            HeadUtils.getNameCache().put(uuid, result.getName());
        }
        else {
            throw new IllegalStateException("Head API Failed! status : " + response.code());
        }
    }
}
