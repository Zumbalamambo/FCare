package in.prasilabs.fcare.services.recognition;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * Created by prasi on 15/12/16.
 */

public class FActivityRecognitionService extends IntentService
{
    private static final String TAG = FActivityRecognitionService.class.getSimpleName();

    public FActivityRecognitionService()
    {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(ActivityRecognitionResult.hasResult(intent))
        {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities() );
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities)
    {
        DetectedActivity possibleActivity = null;
        for(DetectedActivity detectedActivity : probableActivities)
        {
            if(possibleActivity == null || detectedActivity.getConfidence() > possibleActivity.getConfidence()) {
                possibleActivity = detectedActivity;
            }
        }


        if(possibleActivity != null && possibleActivity.getConfidence() > 40) {
            FActivityMonitor.sendActivityStatus(this, possibleActivity);
        }
    }
}
