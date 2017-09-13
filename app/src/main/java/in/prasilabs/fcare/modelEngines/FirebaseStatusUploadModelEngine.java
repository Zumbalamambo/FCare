package in.prasilabs.fcare.modelEngines;

import android.content.Context;
import android.location.Address;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.prasilabs.droidwizardlib.utils.LocalPreference;
import com.prasilabs.enums.UserActivityEnum;
import com.prasilabs.enums.WeatherEnum;
import com.prasilabs.pojos.responsePojos.UserDataVO;

import org.json.JSONObject;

import java.util.List;

import in.prasilabs.fcare.base.BaseApp;
import in.prasilabs.fcare.internalPojos.FirebaseActivityStatus;
import in.prasilabs.fcare.internalPojos.FirebaseBatteryStatus;
import in.prasilabs.fcare.internalPojos.FirebaseLocationStatus;
import in.prasilabs.fcare.internalPojos.FirebasePhoneStatus;
import in.prasilabs.fcare.internalPojos.FirebaseStatusData;
import in.prasilabs.fcare.internalPojos.FirebaseWeatherData;
import in.prasilabs.fcare.internalPojos.WeatherResponsePojo;
import in.prasilabs.fcare.managers.FPowerManager;
import in.prasilabs.fcare.managers.FUserManager;
import in.prasilabs.fcare.services.api.ApiManager;
import in.prasilabs.fcare.services.api.FCallBack;
import in.prasilabs.fcare.services.battery.FBatteryMonitor;
import in.prasilabs.fcare.services.firebaseConfigs.FireBaseConfig;
import in.prasilabs.fcare.utils.JsonUtil;
import in.prasilabs.fcare.utils.LocationUtils;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by prasi on 7/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FirebaseStatusUploadModelEngine extends CoreModelEngine {

    private static FirebaseStatusUploadModelEngine instance;

    private FirebaseStatusData instanceData;

    private static final String KEY = "firebasedata";

    public static FirebaseStatusUploadModelEngine getInstance() {
        if(instance == null) {
            instance = new FirebaseStatusUploadModelEngine();
        }
        return instance;
    }

    private FirebaseStatusData collectSanity(Context context) {
        if (FUserManager.getInstance(context).getUserDataVO() != null) {
            FirebaseStatusData firebaseStatusData = getFirebaseStatusData(context);

            //Setting userid;
            firebaseStatusData.setUserId(FUserManager.getInstance(context).getUserDataVO().getId());

            //Setting update time.
            firebaseStatusData.setLastUpdatedTimeInMillis(System.currentTimeMillis());

            //Setting battery.
            FirebaseBatteryStatus firebaseBatteryStatus = firebaseStatusData.getFirebaseBatteryStatus();
            if(firebaseBatteryStatus == null) {
                firebaseBatteryStatus = new FirebaseBatteryStatus();
            }
            firebaseBatteryStatus.setPercentage(FBatteryMonitor.getBatteryLevel(context).intValue());
            firebaseBatteryStatus.setUpdatedTimeInMillis(System.currentTimeMillis());
            firebaseStatusData.setFirebaseBatteryStatus(firebaseBatteryStatus);

            //Setting location
            FirebaseLocationStatus firebaseLocationStatus = firebaseStatusData.getFirebaseLocationStatus();
            if(firebaseLocationStatus == null) {
                firebaseLocationStatus = new FirebaseLocationStatus();
            }
            firebaseLocationStatus.setEnabled(LocationUtils.isLocationEnabled(context));
            firebaseLocationStatus.setTimeInMillis(System.currentTimeMillis());
            firebaseStatusData.setFirebaseLocationStatus(firebaseLocationStatus);

            //Setting phone status
            FirebasePhoneStatus firebasePhoneStatus = firebaseStatusData.getFirebasePhoneStatus();
            if(firebasePhoneStatus == null) {
                firebasePhoneStatus = new FirebasePhoneStatus();
            }
            firebasePhoneStatus.setScreenOn(FPowerManager.isScreenOn(context));
            firebasePhoneStatus.setUpdatedTimeInMillis(System.currentTimeMillis());
            firebaseStatusData.setFirebasePhoneStatus(firebasePhoneStatus);

            //Setting activity
            FirebaseActivityStatus firebaseActivityStatus = firebaseStatusData.getFirebaseActivityStatus();
            if(firebaseActivityStatus == null) {
                firebaseActivityStatus = new FirebaseActivityStatus();
            }
            firebaseStatusData.setFirebaseActivityStatus(firebaseActivityStatus);

            //Setting weather data
            FirebaseWeatherData firebaseWeatherData = firebaseStatusData.getFirebaseWeatherData();
            if(firebaseWeatherData == null) {
                firebaseWeatherData = new FirebaseWeatherData();
            }
            firebaseStatusData.setFirebaseWeatherData(firebaseWeatherData);

            return firebaseStatusData;
        }

        return null;
    }

    public void uploadBatteryStatus(Context context, int percentage, boolean isCharging) {

        if(FUserManager.getInstance(context).getUserDataVO() != null) {

            FirebaseStatusData firebaseStatusData = collectSanity(context);
            firebaseStatusData.getFirebaseBatteryStatus().setCharging(isCharging);
            firebaseStatusData.getFirebaseBatteryStatus().setPercentage(percentage);

            FireBaseConfig.getFirebaseDatabase(context).getReference(String.valueOf(FUserManager.getInstance(context).getUserDataVO().getId())).setValue(firebaseStatusData);

            saveData(context, firebaseStatusData);
        }
    }

    public void uploadActivityStatus(Context context, UserActivityEnum userActivityEnum) {
        if(FUserManager.getInstance(context).getUserDataVO() != null) {

            FirebaseStatusData firebaseStatusData = collectSanity(context);
            firebaseStatusData.getFirebaseActivityStatus().setUserActivityEnum(userActivityEnum.getName());
            firebaseStatusData.getFirebaseActivityStatus().setUpdatedTimeInMillis(System.currentTimeMillis());

            FireBaseConfig.getFirebaseDatabase(context).getReference(String.valueOf(FUserManager.getInstance(context).getUserDataVO().getId())).setValue(firebaseStatusData);
            saveData(context, firebaseStatusData);
        }
    }

    public void uploadLocationStatus(Context context, final double lat, final double lon, long time) {
        if(FUserManager.getInstance(context).getUserDataVO() != null) {

            FirebaseStatusData firebaseStatusData = collectSanity(context);

            firebaseStatusData.getFirebaseLocationStatus().setLat(lat);
            firebaseStatusData.getFirebaseLocationStatus().setLon(lon);
            firebaseStatusData.getFirebaseLocationStatus().setTimeInMillis(time);
            firebaseStatusData.getFirebaseLocationStatus().setLocality("Fetching...");

            FireBaseConfig.getFirebaseDatabase(context).getReference(String.valueOf(FUserManager.getInstance(context).getUserDataVO().getId())).setValue(firebaseStatusData);

            saveData(context, firebaseStatusData);

            triggerLocalityFetch(lat, lon);
            triggerWeatherFetch(lat, lon);
        }
    }

    private void triggerWeatherFetch(double lat, double lon) {

        WeatherModelEngine.getInstance().fetchWeather(lat, lon, new WeatherModelEngine.WeatherCallback() {
            @Override
            public void getWeather(WeatherResponsePojo weatherResponsePojo) {

                if(weatherResponsePojo != null && weatherResponsePojo.getCurrently() != null) {
                    if (FUserManager.getInstance(BaseApp.getAppContext()).getUserDataVO() != null) {

                        WeatherEnum weatherEnum = null;

                        if(weatherResponsePojo.getCurrently().getIcon() != null) {
                            if (weatherResponsePojo.getCurrently().getIcon().equals("clear-day")) {
                                weatherEnum = WeatherEnum.CLEAR_DAY;
                            } else if (weatherResponsePojo.getCurrently().getIcon().equals("clear-night")) {
                                weatherEnum = WeatherEnum.cLEAR_NIGHT;
                            } else if (weatherResponsePojo.getCurrently().getIcon().equals("rain")) {
                                weatherEnum = WeatherEnum.RAINY;
                            } else if (weatherResponsePojo.getCurrently().getIcon().equals("snow")) {
                                weatherEnum = WeatherEnum.SNOW;
                            } else if (weatherResponsePojo.getCurrently().getIcon().equals("sleet")) {
                                weatherEnum = WeatherEnum.SLEET;
                            } else if (weatherResponsePojo.getCurrently().getIcon().equals("wind")) {
                                weatherEnum = WeatherEnum.WIND;
                            } else if (weatherResponsePojo.getCurrently().getIcon().equals("fog")) {
                                weatherEnum = WeatherEnum.FOG;
                            } else if (weatherResponsePojo.getCurrently().getIcon().equals("cloudy")) {
                                weatherEnum = WeatherEnum.CLOUDY;
                            } else if (weatherResponsePojo.getCurrently().getIcon().equals("partly-cloudy-day")) {
                                weatherEnum = WeatherEnum.PARTLY_CLOUDY_DAY;
                            } else if (weatherResponsePojo.getCurrently().getIcon().equals("partly-cloudy-night")) {
                                weatherEnum = WeatherEnum.PARTLY_CLOUDY_NIGHT;
                            }
                        }

                        FirebaseStatusData firebaseStatusData = collectSanity(BaseApp.getAppContext());
                        firebaseStatusData.getFirebaseWeatherData().setTemperature(weatherResponsePojo.getCurrently().getTemperature());
                        if (weatherEnum != null) {
                            firebaseStatusData.getFirebaseWeatherData().setWeatherEnum(weatherEnum.getName());
                        } else {
                            firebaseStatusData.getFirebaseWeatherData().setWeatherEnum("N/A");
                        }
                        firebaseStatusData.getFirebaseWeatherData().setUpdatedTimeInMillis(System.currentTimeMillis());

                        FireBaseConfig.getFirebaseDatabase(BaseApp.getAppContext()).getReference(String.valueOf(FUserManager.getInstance(BaseApp.getAppContext()).getUserDataVO().getId())).setValue(firebaseStatusData);

                        saveData(BaseApp.getAppContext(), firebaseStatusData);
                    }
                }
            }
        });
    }

    private void triggerLocalityFetch(final double lat, final double lon) {
        runAsync(new RunAsyncCallBack() {
            @Override
            public String run() throws Exception {

                String locality = LocationUtils.getLocationForLatLng(BaseApp.getAppContext(), lat, lon);

                return locality;
            }

            @Override
            public <T> void result(T t) {

                String locality = (String) t;

                if(!TextUtils.isEmpty(locality)) {
                    updateLocation(BaseApp.getAppContext(), locality);
                } else {
                    updateLocation(BaseApp.getAppContext(), "N/A");
                }
            }
        });
    }

    public void updateLocation(Context context, String location) {
        if(FUserManager.getInstance(context).getUserDataVO() != null) {

            FirebaseStatusData firebaseStatusData = collectSanity(context);

            firebaseStatusData.getFirebaseLocationStatus().setLocality(location);

            FireBaseConfig.getFirebaseDatabase(context).getReference(String.valueOf(FUserManager.getInstance(context).getUserDataVO().getId())).setValue(firebaseStatusData);

            saveData(context, firebaseStatusData);
        }
    }

    private void saveData(Context context, FirebaseStatusData firebaseStatusData) {
        JSONObject data = JsonUtil.getJsonFromClass(firebaseStatusData);
        LocalPreference.saveAppDataInShared(context, KEY, String.valueOf(data));

        instanceData = firebaseStatusData;

        setChanged();
        notifyObservers(firebaseStatusData);
    }

    @NonNull
    public synchronized FirebaseStatusData getFirebaseStatusData(Context context) {

        if(instanceData == null) {
            String data = LocalPreference.getAppDataFromShared(context, KEY, null);

            if(!TextUtils.isEmpty(data)) {
                instanceData = JsonUtil.getObjectFromJson(data, FirebaseStatusData.class);
            }
        }

        if(instanceData == null) {
            instanceData = new FirebaseStatusData();
        }

        return instanceData;
    }

    public void uploadSanity(Context context) {
        if(FUserManager.getInstance(context).getUserDataVO() != null) {

            FirebaseStatusData firebaseStatusData = collectSanity(context);

            FireBaseConfig.getFirebaseDatabase(context).getReference(String.valueOf(FUserManager.getInstance(context).getUserDataVO().getId())).setValue(firebaseStatusData);

            saveData(context, firebaseStatusData);
        }
    }
}
