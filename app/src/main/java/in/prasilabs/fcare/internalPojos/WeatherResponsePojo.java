package in.prasilabs.fcare.internalPojos;

import java.util.List;

/**
 * Created by prasi on 8/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class WeatherResponsePojo {

    private double latitude;
    private double longitude;
    private Data currently;
    private DataWithSummary hourly;
    private DataWithSummary daily;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Data getCurrently() {
        return currently;
    }

    public void setCurrently(Data currently) {
        this.currently = currently;
    }

    public DataWithSummary getHourly() {
        return hourly;
    }

    public void setHourly(DataWithSummary hourly) {
        this.hourly = hourly;
    }

    public DataWithSummary getDaily() {
        return daily;
    }

    public void setDaily(DataWithSummary daily) {
        this.daily = daily;
    }

    public static class Data {

        private long time;
        private String summary;
        private String icon;
        private double precipIntensity;
        private double precipProbability;
        private String precipType;
        private double temperature;
        private double apparentTemperature;
        private double humidity;
        private double pressure;
        private double windSpeed;
        private double cloudCover;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public double getPrecipIntensity() {
            return precipIntensity;
        }

        public void setPrecipIntensity(double precipIntensity) {
            this.precipIntensity = precipIntensity;
        }

        public double getPrecipProbability() {
            return precipProbability;
        }

        public void setPrecipProbability(double precipProbability) {
            this.precipProbability = precipProbability;
        }

        public String getPrecipType() {
            return precipType;
        }

        public void setPrecipType(String precipType) {
            this.precipType = precipType;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getApparentTemperature() {
            return apparentTemperature;
        }

        public void setApparentTemperature(double apparentTemperature) {
            this.apparentTemperature = apparentTemperature;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public double getCloudCover() {
            return cloudCover;
        }

        public void setCloudCover(double cloudCover) {
            this.cloudCover = cloudCover;
        }
    }

    public static class DataWithSummary {
        private String summary;
        private String icon;
        private List<Data> data;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
    }


}
