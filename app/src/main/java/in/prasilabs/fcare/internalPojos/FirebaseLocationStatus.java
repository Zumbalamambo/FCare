package in.prasilabs.fcare.internalPojos;

import android.content.Context;
import android.text.TextUtils;

import com.prasilabs.droidwizardlib.utils.JsonUtil;
import com.prasilabs.droidwizardlib.utils.LocalPreference;

import org.json.JSONObject;

/**
 * Created by prasi on 7/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FirebaseLocationStatus {

    private boolean enabled;
    private double lat;
    private double lon;
    private String locality;
    private long timeInMillis;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
