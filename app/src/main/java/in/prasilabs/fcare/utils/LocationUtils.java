package in.prasilabs.fcare.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Contus team on 7/9/17.
 */

public class LocationUtils {

    private static final String TAG = LocationUtils.class.getSimpleName();

    public static boolean isLocationEnabled(Context context)
    {
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if(lm == null)
            {
                lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            }

            return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        return false;
    }


    public static void askEnableLocationRequest(final Activity activity, final int reqCode)
    {
        if(Utils.isGooglePlayInstalled(activity)) {
            ConsoleLog.i(TAG, "ask location called");
            GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(@Nullable Bundle bundle) {
                    ConsoleLog.i(TAG, "onconnected");
                }

                @Override
                public void onConnectionSuspended(int i) {
                    ConsoleLog.i(TAG, " suspended");
                }
            };

            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {

                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    ConsoleLog.w(TAG, "failed");
                    ConsoleLog.i(TAG, " connection result is " + connectionResult.getErrorMessage() + " code " + connectionResult.getErrorCode());
                    DialogUtils.askEnableLocationDialog(activity);
                }
            };

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(activity)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(connectionCallbacks)
                    .addOnConnectionFailedListener(onConnectionFailedListener).build();

            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true); //this is the key ingredient

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(activity, reqCode);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't showEventInfo the dialog.
                            break;
                    }
                }
            });
        }
        else
        {
            DialogUtils.askEnableLocationDialog(activity);
        }
    }


    public static String getCityAddressFromGoogle(List<Address> addresses) {
        String displayCity = "";
        if (addresses != null && addresses.size() > 0) {

            String city = addresses.get(0).getLocality();

            displayCity = city;
            ConsoleLog.i(TAG, "display city is : " + displayCity);
        }

        return displayCity;
    }

    public static String getLocalityAddressFromGoogle(String addresses) {
        String locality = "";

        String[] strings = addresses.split(",");

        if (strings.length > 3) {
            locality = strings[strings.length - 4];
        } else if (strings.length > 2) {
            locality = strings[1];
        } else if (strings.length > 1) {
            locality = strings[0];
        } else {
            locality = addresses;
        }

        return locality;
    }

    private static List<Address> getStringFromLocation(double lat, double lng)
    {
        List<Address> retList = new ArrayList<>();

        try
        {
            String gUrl = "http://maps.googleapis.com/maps/api/geocode/json";
            Map<String,String> mParams = new HashMap<>();
            mParams.put("latlng", lat+","+lng);
            mParams.put("key", "AIzaSyCjbZWU57c6ljLzm_v-ryg3htCICOFBIAA");
            mParams.put("language", Locale.getDefault().getCountry());

            String urlString = constructUrl(gUrl, mParams);

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);


            int status = connection.getResponseCode();
            InputStream content;

            // RZP: You need to check for status code
            if(status >= 400)
            {
                content = connection.getErrorStream();
            }
            else
            {
                content = connection.getInputStream();
            }

            BufferedReader in = new BufferedReader (new InputStreamReader(content));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            in.close();


            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    String indiStr = result.getString("formatted_address");
                    Address addr = new Address(Locale.getDefault());
                    addr.setAddressLine(0, indiStr);
                    retList.add(addr);
                }
            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return retList;
    }

    public static String constructUrl(String url, Map<String, String> mParams) {
        StringBuilder builder = new StringBuilder();

        if (mParams != null && mParams.size() > 0) {
            for (String key : mParams.keySet()) {
                String value = String.valueOf(mParams.get(key));
                if (value != null) {
                    try {
                        value = URLEncoder.encode(String.valueOf(value), "UTF-8");


                        if (builder.length() > 0)
                            builder.append("&");
                        builder.append(key).append("=").append(value);
                    } catch (UnsupportedEncodingException e) {
                    }
                }
            }

            if (url.contains("?")) {
                url += "&" + builder.toString();
            } else {
                url += "?" + builder.toString();
            }
        }

        ConsoleLog.i(TAG, "Built URL " + url);
        return url;
    }

    public static String getLocationForLatLng(Context context, double lat, double lon)
    {
        String address = null;

        try
        {
            final Geocoder geocoder = new Geocoder(context);

            List<Address> sourceAddressList = null;
            try {
                sourceAddressList = geocoder.getFromLocation(lat, lon, 1);
            } catch (Exception e) {
                ConsoleLog.e(e);
            }

            if (sourceAddressList == null)
            {
                sourceAddressList = LocationUtils.getStringFromLocation(lat, lon);
            }

            if (sourceAddressList != null && sourceAddressList.size() > 0)
            {
                address = LocationUtils.getLocalityAddressFromGoogle(LocationUtils.getDisplayAddressFromGoogle(sourceAddressList));

                ConsoleLog.i(TAG, "source address is : " + address);
            } else {
                ConsoleLog.w(TAG, "address is empty for lat lon : " + lat + " : " + lon);
            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return address;
    }

    public static String getDisplayAddressFromGoogle(List<Address> addresses) {
        String displayLocality = "";
        if (addresses != null && addresses.size() > 0) {

            String locality = addresses.get(0).getSubLocality();
            String city = addresses.get(0).getLocality();

            displayLocality = (!TextUtils.isEmpty(locality)) ? ViewUtil.toTitleCase(locality) : "";
            displayLocality += (!TextUtils.isEmpty(displayLocality) && !TextUtils.isEmpty(city)) ? ", " + ViewUtil.toTitleCase(city) :
                    (!TextUtils.isEmpty(city)) ? ViewUtil.toTitleCase(city) : "";

            ConsoleLog.i(TAG, "display locality is : " + displayLocality);
        }

        return displayLocality;
    }

    public interface GetLocationNameCallBack {
        void getLocationName(String location);
    }

}
