package kr.teamcocoa.freefight.retrofit.head;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.UUID;

public interface MojangSessionService {

    @GET("session/minecraft/profile/{uuid}")
    Call<MojangSessionResult> getInfo(@Path("uuid") UUID uuid);

}
