package in.prasilabs.fcare.internalPojos;

import com.prasilabs.enums.WeatherEnum;

/**
 * Created by prasi on 9/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FirebaseWeatherData {
    private String weatherEnum;
    private double temperature;
    private long updatedTimeInMillis;

    public String getWeatherEnum() {
        return weatherEnum;
    }

    public void setWeatherEnum(String weatherEnum) {
        this.weatherEnum = weatherEnum;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public long getUpdatedTimeInMillis() {
        return updatedTimeInMillis;
    }

    public void setUpdatedTimeInMillis(long updatedTimeInMillis) {
        this.updatedTimeInMillis = updatedTimeInMillis;
    }
}
