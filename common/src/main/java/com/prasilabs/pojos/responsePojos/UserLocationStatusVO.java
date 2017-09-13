package com.prasilabs.pojos.responsePojos;

import com.prasilabs.pojos.GeoPoint;

/**
 * Created by prasi on 2/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class UserLocationStatusVO {

    private Long id;

    private GeoPoint location;

    private boolean isLocationEnabled;

    private long lastLocationUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public boolean isLocationEnabled() {
        return isLocationEnabled;
    }

    public void setLocationEnabled(boolean locationEnabled) {
        isLocationEnabled = locationEnabled;
    }

    public long getLastLocationUpdateTime() {
        return lastLocationUpdateTime;
    }

    public void setLastLocationUpdateTime(long lastLocationUpdateTime) {
        this.lastLocationUpdateTime = lastLocationUpdateTime;
    }
}
