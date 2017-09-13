package in.prasilabs.fcare.managers;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by prasi on 8/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FPowerManager {

    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return pm.isInteractive();
    }
}
