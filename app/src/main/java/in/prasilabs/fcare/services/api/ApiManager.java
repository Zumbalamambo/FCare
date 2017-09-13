package in.prasilabs.fcare.services.api;

import android.text.TextUtils;

import com.prasilabs.constants.CommonConstant;
import com.prasilabs.pojos.responsePojos.UserDataVO;

import java.io.IOException;

import in.prasilabs.fcare.BuildConfig;
import in.prasilabs.fcare.base.BaseApp;
import in.prasilabs.fcare.managers.FUserManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Contus team on 29/8/17.
 */

public final class ApiManager {

    private final UserRequest userRequest;
    private final StatusRequest statusRequest;
    private final UserRelationRequest userRelationRequest;
    private final WeatherRequest weatherRequest;

    private static ApiManager instance;

    public static ApiManager getInstance() {
        if(instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    private ApiManager(){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder ongoing = chain.request().newBuilder();
                UserDataVO userDataVo = FUserManager.getInstance(BaseApp.getAppContext()).getUserDataVO();
                if (userDataVo != null && !TextUtils.isEmpty(userDataVo.getUserHash())) {
                    ongoing.addHeader(CommonConstant.HASH_AUTH_STR, userDataVo.getUserHash());
                }
                return chain.proceed(ongoing.build());
            }
        });

        builder.addInterceptor(loggingInterceptor);

        Retrofit appEngine = new Retrofit.Builder().client(builder.build())
                .baseUrl("https://flocation-148113.appspot.com/_ah/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit weatherApi = new Retrofit.Builder().client(builder.build())
                .baseUrl("https://api.darksky.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        userRequest = appEngine.create(UserRequest.class);
        statusRequest = appEngine.create(StatusRequest.class);
        userRelationRequest = appEngine.create(UserRelationRequest.class);
        weatherRequest = weatherApi.create(WeatherRequest.class);
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public StatusRequest getStatusRequest() {
        return statusRequest;
    }

    public UserRelationRequest getUserRelationRequest() {
        return userRelationRequest;
    }

    public WeatherRequest getWeatherRequest() {
        return weatherRequest;
    }
}
