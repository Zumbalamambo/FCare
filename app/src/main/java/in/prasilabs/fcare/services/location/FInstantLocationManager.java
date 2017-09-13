package in.prasilabs.fcare.services.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import java.util.concurrent.TimeUnit;

/**
 * Created by prasi on 6/12/16.
 */

public class FInstantLocationManager implements LocationListener, android.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = FInstantLocationManager.class.getSimpleName();

    private LocationCallBack locationCallBack;
    private Context context;

    private GoogleApiClient mGoogleApiClient;
    private LocationManager locationManager;

    public void getLocation(final Context context, LocationCallBack locationCallBack)
    {
        this.context = context;
        this.locationCallBack = locationCallBack;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setBearingRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        criteria.setSpeedRequired(false);
        criteria.setAltitudeRequired(false);

        String provider = locationManager.getBestProvider(criteria, false);

        if(locationManager.isProviderEnabled(provider))
        {
            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mGoogleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
                mGoogleApiClient.connect();

                Location location = locationManager.getLastKnownLocation(provider);

                locationManager.requestLocationUpdates(provider, TimeUnit.MINUTES.toMillis(1), 1000, this);

                if(location != null && (location.getTime() - System.currentTimeMillis() > TimeUnit.SECONDS.toMillis(10)) && location.hasAccuracy() && location.getAccuracy() < 250)
                {
                    if(locationCallBack != null)
                    {
                        locationCallBack.getLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            removeLocationUpdates();
                        }
                    },5000);
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if(location != null && locationCallBack != null)
        {
            locationCallBack.getLocation(new LatLng(location.getLatitude(), location.getLongitude()));

            removeLocationUpdates();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void removeLocationUpdates()
    {
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        if(locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        if(checkLocationPermission(context))
        {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(TimeUnit.MINUTES.toMillis(10));
            locationRequest.setFastestInterval(TimeUnit.MINUTES.toMillis(5));
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, locationRequest, this);
        }
    }

    private boolean checkLocationPermission(Context context)
    {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onConnectionSuspended(int i)
    {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        ConsoleLog.w(TAG, "connection failed");
    }

    public interface LocationCallBack
    {
        void getLocation(LatLng latLng);
    }
}
