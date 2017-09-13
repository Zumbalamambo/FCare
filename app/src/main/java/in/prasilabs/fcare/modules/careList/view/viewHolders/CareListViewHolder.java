package in.prasilabs.fcare.modules.careList.view.viewHolders;

import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prasilabs.enums.UserActivityEnum;
import com.prasilabs.enums.WeatherEnum;
import com.prasilabs.pojos.responsePojos.UserDataVO;
import com.prasilabs.utils.DataUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.prasilabs.fcare.R;
import in.prasilabs.fcare.internalPojos.FirebaseActivityStatus;
import in.prasilabs.fcare.internalPojos.FirebaseBatteryStatus;
import in.prasilabs.fcare.internalPojos.FirebaseLocationStatus;
import in.prasilabs.fcare.internalPojos.FirebaseStatusData;
import in.prasilabs.fcare.internalPojos.FirebaseWeatherData;
import in.prasilabs.fcare.internalPojos.RelationShipWithStatusData;
import in.prasilabs.fcare.modules.careList.view.CareListCallback;
import in.prasilabs.fcare.utils.ConversionUtils;
import in.prasilabs.fcare.utils.Utils;
import in.prasilabs.fcare.utils.ViewUtil;

/**
 * Created by Contus team on 5/9/17.
 */

public class CareListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.user_image)
    ImageView userImage;

    @BindView(R.id.user_name)
    TextView userNameText;

    @BindView(R.id.location_layout)
    LinearLayout localityLayout;

    @BindView(R.id.location_icon)
    ImageView localityIcon;

    @BindView(R.id.locality_text)
    TextView localityText;

    @BindView(R.id.locality_time_text)
    TextView localityTimeText;

    @BindView(R.id.battery_layout)
    LinearLayout batteryLayout;

    @BindView(R.id.battery_icon)
    ImageView batteryIcon;

    @BindView(R.id.battery_text)
    TextView batteryText;

    @BindView(R.id.battery_time_text)
    TextView batteryTimeText;

    @BindView(R.id.activity_layout)
    LinearLayout activityLayout;

    @BindView(R.id.activity_icon)
    ImageView activityIcon;

    @BindView(R.id.activity_text)
    TextView activityText;

    @BindView(R.id.status_time_text)
    TextView activityTimeText;

    @BindView(R.id.icon_weather)
    ImageView weatherIcon;

    @BindView(R.id.text_climate)
    TextView climateText;

    @BindView(R.id.text_temp)
    TextView tempText;

    @BindView(R.id.weather_divider)
    View weatherDivider;

    @BindView(R.id.weather_layout)
    LinearLayout weatherLayout;

    private CareListCallback careListCallback;

    public CareListViewHolder(View itemView, CareListCallback careListCallback) {
        super(itemView);
        this.careListCallback = careListCallback;

        ButterKnife.bind(this, itemView);
    }

    public void renderData(RelationShipWithStatusData relationShipWithStatusData) {

        UserDataVO userDataVO = relationShipWithStatusData.getUserRelationShipVO().getUserDataVO();

        ViewUtil.renderImage(userImage, userDataVO.getUserPictureUrl(), true, userDataVO.getName());
        userNameText.setText(ViewUtil.toCamelCase(userDataVO.getName()));

        FirebaseStatusData firebaseStatusData = relationShipWithStatusData.getFirebaseStatusData();

        if (firebaseStatusData != null) {
            renderLocality(firebaseStatusData.getFirebaseLocationStatus());

            renderActivity(firebaseStatusData.getFirebaseActivityStatus());

            renderBattery(firebaseStatusData.getFirebaseBatteryStatus());

            renderWeatherStatus(firebaseStatusData.getFirebaseWeatherData());
        }
    }

    private void renderActivity(FirebaseActivityStatus firebaseActivityStatus) {
        if (firebaseActivityStatus != null && firebaseActivityStatus.getUserActivityEnum() != null) {

            String userActivityEnum = firebaseActivityStatus.getUserActivityEnum();

            if (userActivityEnum.equals(UserActivityEnum.ONLINE.getName())) {
                activityIcon.setBackground(ActivityCompat.getDrawable(activityIcon.getContext(), R.drawable.ic_activity_online));
                activityText.setText("Active");
            } else if (userActivityEnum.equals(UserActivityEnum.BIKING.getName())) {
                activityIcon.setBackground(ActivityCompat.getDrawable(activityIcon.getContext(), R.drawable.ic_activity_biking));
                activityText.setText("Biking");
            } else if (userActivityEnum.equals(UserActivityEnum.DRIVING.getName())) {
                activityIcon.setBackground(ActivityCompat.getDrawable(activityIcon.getContext(), R.drawable.ic_activity_biking));
                activityText.setText("Driving");
            } else if (userActivityEnum.equals(UserActivityEnum.RUNNING.getName())) {
                activityIcon.setBackground(ActivityCompat.getDrawable(activityIcon.getContext(), R.drawable.ic_activity_run));
                activityText.setText("Running");
            } else if (userActivityEnum.equals(UserActivityEnum.WALKING.getName())) {
                activityIcon.setBackground(ActivityCompat.getDrawable(activityIcon.getContext(), R.drawable.ic_activity_walk));
                activityText.setText("Walking");
            } else if (userActivityEnum.equals(UserActivityEnum.IDLE.getName())) {
                activityIcon.setBackground(ActivityCompat.getDrawable(activityIcon.getContext(), R.drawable.ic_activity_idle));
                activityText.setText("Phone Idle");
            }

            activityTimeText.setText(ViewUtil.getRelativeTime(firebaseActivityStatus.getUpdatedTimeInMillis()));
            activityTimeText.setVisibility(View.VISIBLE);
        } else {
            activityText.setText("N/A");
            activityTimeText.setVisibility(View.GONE);
        }
    }

    private void renderLocality(final FirebaseLocationStatus firebaseLocationStatus) {
        if (firebaseLocationStatus != null) {
            if (firebaseLocationStatus.isEnabled()) {
                if (firebaseLocationStatus.getLocality() != null) {
                    localityText.setText(firebaseLocationStatus.getLocality());
                } else {
                    localityText.setText("Unknown");
                }

                localityText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.openGoogleMap(localityText.getContext(), firebaseLocationStatus.getLat(), firebaseLocationStatus.getLon(), "User");
                    }
                });
            } else {
                localityText.setText("DISABLED");
                localityTimeText.setVisibility(View.GONE);
            }
            localityTimeText.setText(ViewUtil.getRelativeTime(firebaseLocationStatus.getTimeInMillis()));
            localityTimeText.setVisibility(View.VISIBLE);
        } else {
            localityText.setText("N/A");
            localityTimeText.setVisibility(View.GONE);
        }
    }

    private void renderBattery(FirebaseBatteryStatus firebaseBatteryStatus) {
        if (firebaseBatteryStatus != null && firebaseBatteryStatus.getPercentage() > 0) {

            int percentage = firebaseBatteryStatus.getPercentage();

            if (firebaseBatteryStatus.isCharging()) {
                if (percentage > 60) {
                    batteryIcon.setBackground(ActivityCompat.getDrawable(batteryIcon.getContext(), R.drawable.ic_battery_charging_good));
                } else {
                    batteryIcon.setBackground(ActivityCompat.getDrawable(batteryIcon.getContext(), R.drawable.ic_battery_charging_low));
                }

                batteryText.setText(String.valueOf(firebaseBatteryStatus.getPercentage()) + "(CHARGING)");
            } else {
                if (percentage > 90) {
                    batteryIcon.setBackground(ActivityCompat.getDrawable(batteryIcon.getContext(), R.drawable.ic_battery_full));
                } else if (percentage > 60) {
                    batteryIcon.setBackground(ActivityCompat.getDrawable(batteryIcon.getContext(), R.drawable.ic_battery_60));
                } else if (percentage > 30) {
                    batteryIcon.setBackground(ActivityCompat.getDrawable(batteryIcon.getContext(), R.drawable.ic_battery_30));
                } else if (percentage > 0) {
                    batteryIcon.setBackground(ActivityCompat.getDrawable(batteryIcon.getContext(), R.drawable.ic_battery_alert));
                }

                batteryText.setText(String.valueOf(firebaseBatteryStatus.getPercentage()));
            }

            batteryTimeText.setText(ViewUtil.getRelativeTime(firebaseBatteryStatus.getUpdatedTimeInMillis()));
            batteryTimeText.setVisibility(View.VISIBLE);
        } else {
            batteryText.setText("N/A");
            batteryTimeText.setVisibility(View.GONE);
        }
    }

    private void renderWeatherStatus(FirebaseWeatherData firebaseWeatherData) {

        if (firebaseWeatherData != null && firebaseWeatherData.getWeatherEnum() != null) {
            if (firebaseWeatherData.getWeatherEnum().equals(WeatherEnum.CLEAR_DAY.getName())) {
                weatherIcon.setBackground(ActivityCompat.getDrawable(weatherIcon.getContext(), R.drawable.ic_sun));
                climateText.setText("SUNNY");
            } else if (firebaseWeatherData.getWeatherEnum().equals(WeatherEnum.cLEAR_NIGHT.getName())) {
                weatherIcon.setBackground(ActivityCompat.getDrawable(weatherIcon.getContext(), R.drawable.ic_moon));
                climateText.setText("CLEAR MOON");
            } else if (firebaseWeatherData.getWeatherEnum().equals(WeatherEnum.PARTLY_CLOUDY_DAY.getName())) {
                weatherIcon.setBackground(ActivityCompat.getDrawable(weatherIcon.getContext(), R.drawable.ic_weather_partlycloudy));
                climateText.setText("PARTLY CLOUDY");
            } else if (firebaseWeatherData.getWeatherEnum().equals(WeatherEnum.PARTLY_CLOUDY_NIGHT.getName())) {
                weatherIcon.setBackground(ActivityCompat.getDrawable(weatherIcon.getContext(), R.drawable.ic_weather_partlycloudy));
                climateText.setText("PARTLY CLOUDY");
            } else if (firebaseWeatherData.getWeatherEnum().equals(WeatherEnum.CLOUDY.getName())) {
                weatherIcon.setBackground(ActivityCompat.getDrawable(weatherIcon.getContext(), R.drawable.ic_cloud));
                climateText.setText("CLOUDY");
            } else if (firebaseWeatherData.getWeatherEnum().equals(WeatherEnum.RAINY.getName())) {
                weatherIcon.setBackground(ActivityCompat.getDrawable(weatherIcon.getContext(), R.drawable.ic_rain));
                climateText.setText("RAINY");
            } else if (firebaseWeatherData.getWeatherEnum().equals(WeatherEnum.SNOW.getName())) {
                weatherIcon.setBackground(ActivityCompat.getDrawable(weatherIcon.getContext(), R.drawable.ic_snow));
                climateText.setText("SNOW");
            } else if (firebaseWeatherData.getWeatherEnum().equals(WeatherEnum.SLEET.getName())) {
                weatherIcon.setBackground(ActivityCompat.getDrawable(weatherIcon.getContext(), R.drawable.ic_snow));
                climateText.setText("SLEETY");
            } else if (firebaseWeatherData.getWeatherEnum().equals(WeatherEnum.WIND.getName())) {
                weatherIcon.setBackground(ActivityCompat.getDrawable(weatherIcon.getContext(), R.drawable.ic_snow));
                climateText.setText("WINDY");
            }

            tempText.setText(DataUtil.getTruncatedDouble(ConversionUtils.convertToCelcius(firebaseWeatherData.getTemperature())));

            weatherDivider.setVisibility(View.VISIBLE);
            weatherLayout.setVisibility(View.VISIBLE);
        } else {
            weatherDivider.setVisibility(View.GONE);
            weatherLayout.setVisibility(View.GONE);
        }
    }
}
