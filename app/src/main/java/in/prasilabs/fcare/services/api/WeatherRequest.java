package in.prasilabs.fcare.services.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.prasilabs.pojos.responsePojos.UserDataVO;

import in.prasilabs.fcare.internalPojos.WeatherResponsePojo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Contus team on 29/8/17.
 */

public interface WeatherRequest {

    @GET("forecast/{access_token}/{lat_lon}")
    Call<WeatherResponsePojo> getWeatherResponse(@Path("access_token") String accessToken, @Path("lat_lon") String latlon);
}
