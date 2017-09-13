package in.prasilabs.fcare.base;

import com.crashlytics.android.Crashlytics;
import com.prasilabs.droidwizardlib.core.CoreApp;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import in.prasilabs.fcare.BuildConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Contus team on 29/8/17.
 */

public class BaseApp extends CoreApp {

    @Override
    public void onCreate() {
        super.onCreate();

        ConsoleLog.setDebugMode(BuildConfig.DEBUG);
        Fabric.with(this, new Crashlytics());
    }
}
