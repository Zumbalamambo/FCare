package in.prasilabs.fcare.services.battery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.annotation.NonNull;

import in.prasilabs.fcare.modelEngines.FirebaseStatusUploadModelEngine;

/**
 * Created by prasi on 31/8/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FBatteryMonitor {

    private FBatteryMonitor(){}

    public static void uploadStatus(Context context, boolean isCharging) {

        int percentage = getBatteryLevel(context).intValue();

        FirebaseStatusUploadModelEngine.getInstance().uploadBatteryStatus(context, percentage, isCharging);
    }

    @NonNull
    public static Float getBatteryLevel(@NonNull Context context)
    {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        int level = 0;
        int scale = 0;
        if(batteryIntent != null)
        {
            level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }

        if(level > 0 && scale > 0)
        {
            return ((float) level/(float) scale)*100.0f;
        }

        return 0f;
    }
}
