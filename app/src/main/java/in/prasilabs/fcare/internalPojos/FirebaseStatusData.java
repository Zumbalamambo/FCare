package in.prasilabs.fcare.internalPojos;

import com.prasilabs.CacheData;

/**
 * Created by prasi on 7/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FirebaseStatusData implements CacheData {

    private Long userId;
    private long lastUpdatedTimeInMillis;
    private FirebaseActivityStatus firebaseActivityStatus;
    private FirebaseLocationStatus firebaseLocationStatus;
    private FirebaseBatteryStatus firebaseBatteryStatus;
    private FirebasePhoneStatus firebasePhoneStatus;
    private FirebaseWeatherData firebaseWeatherData;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getLastUpdatedTimeInMillis() {
        return lastUpdatedTimeInMillis;
    }

    public void setLastUpdatedTimeInMillis(long lastUpdatedTimeInMillis) {
        this.lastUpdatedTimeInMillis = lastUpdatedTimeInMillis;
    }

    public FirebaseActivityStatus getFirebaseActivityStatus() {
        return firebaseActivityStatus;
    }

    public void setFirebaseActivityStatus(FirebaseActivityStatus firebaseActivityStatus) {
        this.firebaseActivityStatus = firebaseActivityStatus;
    }

    public FirebaseLocationStatus getFirebaseLocationStatus() {
        return firebaseLocationStatus;
    }

    public void setFirebaseLocationStatus(FirebaseLocationStatus firebaseLocationStatus) {
        this.firebaseLocationStatus = firebaseLocationStatus;
    }

    public FirebaseBatteryStatus getFirebaseBatteryStatus() {
        return firebaseBatteryStatus;
    }

    public void setFirebaseBatteryStatus(FirebaseBatteryStatus firebaseBatteryStatus) {
        this.firebaseBatteryStatus = firebaseBatteryStatus;
    }

    public FirebasePhoneStatus getFirebasePhoneStatus() {
        return firebasePhoneStatus;
    }

    public void setFirebasePhoneStatus(FirebasePhoneStatus firebasePhoneStatus) {
        this.firebasePhoneStatus = firebasePhoneStatus;
    }

    public FirebaseWeatherData getFirebaseWeatherData() {
        return firebaseWeatherData;
    }

    public void setFirebaseWeatherData(FirebaseWeatherData firebaseWeatherData) {
        this.firebaseWeatherData = firebaseWeatherData;
    }

    @Override
    public String getKey() {
        return String.valueOf(userId);
    }
}
