package in.prasilabs.fcare.services.api;

import com.prasilabs.pojos.GeoPoint;
import com.prasilabs.pojos.responsePojos.ApiResponseVO;
import com.prasilabs.pojos.responsePojos.UserDataVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Contus team on 29/8/17.
 */

public interface StatusRequest {

    @POST("statusApi/v1/uploadLocationStatus/{isLocationEnabled}/{timeInMillis}")
    Call<ApiResponseVO> uploadLocationStatus(@Path("isLocationEnabled") boolean isLocationEnabled, @Path("timeInMillis") long timeInMillis, @Body GeoPoint geoPoint);

    @POST("statusApi/v1/uploadBatteryStatus/{percentage}/{isCharging}")
    Call<ApiResponseVO> uploadBatteryStatus(@Path("percentage") int percentage, @Path("isCharging") boolean isCharging);
}
