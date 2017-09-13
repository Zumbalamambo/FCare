package in.prasilabs.fcare.services.battery;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by prasi on 31/8/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class BatteryStatusReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
            boolean isBatteryFull = status == BatteryManager.BATTERY_STATUS_FULL;

            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            FBatteryMonitor.uploadStatus(context, isCharging || usbCharge || acCharge);

        } else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            FBatteryMonitor.uploadStatus(context, false);
        }else if(intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            FBatteryMonitor.uploadStatus(context, false);
        } else if(intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)) {
            FBatteryMonitor.uploadStatus(context, false);
        }
    }
}
