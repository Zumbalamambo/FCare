package in.prasilabs.fcare.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Contus team on 7/9/17.
 */

public class PermissionUtils {

    public static boolean checkMandatoryPermission(Context context) {
        boolean isPermissionGranted = true;

        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = false;
        }

        return isPermissionGranted;
    }
}
