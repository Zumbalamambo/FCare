package in.prasilabs.fcare.services.location;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import java.util.concurrent.TimeUnit;

import in.prasilabs.fcare.modelEngines.FirebaseStatusUploadModelEngine;
import in.prasilabs.fcare.services.battery.FBatteryMonitor;
import in.prasilabs.fcare.services.recognition.FActivityRecognitionService;

/**
 * Created by prasi on 1/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FBackGroundLocationAndActivityFetcher extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = FBackGroundLocationAndActivityFetcher.class.getSimpleName();
    private static FBackGroundLocationAndActivityFetcher self;
    private LocationManager lm;
    private GoogleApiClient mGoogleApiClient;

    private BroadcastReceiver screenBroadCastReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            } else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

            }
        }
    };

    public static void startLocationService(Context context)
    {
        Intent intent = new Intent(context, FBackGroundLocationAndActivityFetcher.class);
        intent.setFlags(START_REDELIVER_INTENT);
        context.startService(intent);
    }

    private static void stopService() {
        try {
            if (self != null) {
                self.stopSelf();
            }
        } catch (Exception e) {
            ConsoleLog.e(e);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (lm == null) {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        self = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();

        return START_STICKY;
    }

    private void init() {
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (checkLocationPermission(this)) {
                ConsoleLog.i(TAG, "location registered");
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .addApi(ActivityRecognition.API).build();
                mGoogleApiClient.connect();
            }
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenBroadCastReciever, intentFilter);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

        mGoogleApiClient.disconnect();

        removeLocationUpdates();
    }

    private boolean checkLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void removeLocationUpdates()
    {
        if(mGoogleApiClient.isConnected())
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            FirebaseStatusUploadModelEngine.getInstance().uploadLocationStatus(this, location.getLatitude(), location.getLongitude(), location.getTime());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(checkLocationPermission(this))
        {
            if(mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(TimeUnit.MINUTES.toMillis(10));
                locationRequest.setFastestInterval(TimeUnit.MINUTES.toMillis(10));
                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, locationRequest, this);

                Intent intent = new Intent(this, FActivityRecognitionService.class);
                PendingIntent pendingIntent = PendingIntent.getService(this, 7, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mGoogleApiClient, TimeUnit.MINUTES.toMillis(1), pendingIntent);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ConsoleLog.w(TAG, "connection failed");
    }
}
