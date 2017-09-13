package in.prasilabs.fcare.services.bootReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import in.prasilabs.fcare.managers.FInitManager;

/**
 * Created by Contus team on 7/9/17.
 */

public class FBootReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FInitManager.init(context);
    }
}
