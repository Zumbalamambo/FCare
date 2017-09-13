package in.prasilabs.fcare.services.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.prasilabs.pojos.responsePojos.UserDataVO;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Contus team on 29/8/17.
 */

public interface UserRequest {

    @POST("userApi/v1/verifyOTP/{access_token}")
    Call<UserDataVO> verifyOTP(@Path("access_token") String accessToken);

    @POST("userApi/v1/setUserDetails/{name}/{device_id}")
    Call<UserDataVO> setUserData(@NonNull @Path("name") String name, @NonNull @Path("device_id") String deviceId, @Nullable @Query("picture") String picture);
}
