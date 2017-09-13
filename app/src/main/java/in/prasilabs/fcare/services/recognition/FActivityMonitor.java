package in.prasilabs.fcare.services.recognition;

import android.content.Context;

import com.google.android.gms.location.DetectedActivity;
import com.prasilabs.enums.UserActivityEnum;

import in.prasilabs.fcare.modelEngines.FirebaseStatusUploadModelEngine;
import in.prasilabs.fcare.services.battery.FBatteryMonitor;

/**
 * Created by prasi on 7/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FActivityMonitor {

    private FActivityMonitor(){}

    public static void sendActivityStatus(Context context, DetectedActivity detectedActivity) {

        UserActivityEnum userActivityEnum = null;
        if(detectedActivity.getType() == DetectedActivity.IN_VEHICLE) {
            userActivityEnum = UserActivityEnum.DRIVING;
        } else if(detectedActivity.getType() == DetectedActivity.ON_BICYCLE) {
            userActivityEnum = UserActivityEnum.BIKING;
        } else if(detectedActivity.getType() == DetectedActivity.ON_FOOT || detectedActivity.getType() == DetectedActivity.WALKING) {
            userActivityEnum = UserActivityEnum.WALKING;
        } else if(detectedActivity.getType() == DetectedActivity.RUNNING) {
            userActivityEnum = UserActivityEnum.RUNNING;
        } else if(detectedActivity.getType() == DetectedActivity.UNKNOWN) {
            userActivityEnum = UserActivityEnum.ONLINE;
        } else if(detectedActivity.getType() == DetectedActivity.STILL) {
            userActivityEnum = UserActivityEnum.IDLE;
        }

        if(userActivityEnum != null) {
            FirebaseStatusUploadModelEngine.getInstance().uploadActivityStatus(context, userActivityEnum);
        }
    }
}
