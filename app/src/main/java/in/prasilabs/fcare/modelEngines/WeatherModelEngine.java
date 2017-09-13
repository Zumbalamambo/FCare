package in.prasilabs.fcare.modelEngines;

import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.prasilabs.pojos.responsePojos.UserDataVO;

import in.prasilabs.fcare.internalPojos.WeatherResponsePojo;
import in.prasilabs.fcare.services.api.ApiManager;
import in.prasilabs.fcare.services.api.FCallBack;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by prasi on 8/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class WeatherModelEngine extends CoreModelEngine {

    private static final String WEATHER_API_KEY = "54308bd983ea2dbf43e7227e1a2a7784";

    public static WeatherModelEngine instance;

    public static WeatherModelEngine getInstance() {
        if(instance == null) {
            instance = new WeatherModelEngine();
        }
        return instance;
    }

    public void fetchWeather(double lat, double lon, final WeatherCallback weatherCallback) {

        String latLon = lat + "," + lon;

        final Call<WeatherResponsePojo> otpCall = ApiManager.getInstance().getWeatherRequest().getWeatherResponse(WEATHER_API_KEY, latLon);
        otpCall.enqueue(new FCallBack<WeatherResponsePojo>() {
            @Override
            public void onResponse(Call<WeatherResponsePojo> call, Response<WeatherResponsePojo> response) {

                WeatherResponsePojo weatherResponsePojo = response.body();

                if(weatherCallback != null) {
                    weatherCallback.getWeather(weatherResponsePojo);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponsePojo> call, Throwable t) {

                if(weatherCallback != null) {
                    weatherCallback.getWeather(null);
                }
            }
        });
    }

    public interface WeatherCallback {
        void getWeather(WeatherResponsePojo weatherResponsePojo);
    }
}
