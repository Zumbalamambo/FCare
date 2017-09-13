package in.prasilabs.fcare.managers;

import android.content.Context;

import in.prasilabs.fcare.services.firebaseConfigs.FirebaseStatusListenerService;
import in.prasilabs.fcare.services.location.FBackGroundLocationAndActivityFetcher;
import in.prasilabs.fcare.services.scheduledJob.ScheduledJobService;

/**
 * Created by Contus team on 7/9/17.
 */

public class FInitManager {

    public static void init(Context context) {
        FBackGroundLocationAndActivityFetcher.startLocationService(context);
        FirebaseStatusListenerService.startFirebaseService(context);
        ScheduledJobService.scheduleStatusUpdate(context);
    }
}