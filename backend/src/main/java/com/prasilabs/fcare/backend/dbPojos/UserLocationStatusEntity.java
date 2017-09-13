package com.prasilabs.fcare.backend.dbPojos;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

/**
 * Created by prasi on 2/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

@Entity
public class UserLocationStatusEntity {

    @Id
    private Long id;

    @Parent
    private Key<UserEntity> userEntityKey;

    @Index
    private GeoPt location;

    @Index
    private boolean isLocationEnabled;

    @Index
    private long lastUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key<UserEntity> getUserEntityKey() {
        return userEntityKey;
    }

    public void setUserEntityKey(Key<UserEntity> userEntityKey) {
        this.userEntityKey = userEntityKey;
    }

    public GeoPt getLocation() {
        return location;
    }

    public void setLocation(GeoPt location) {
        this.location = location;
    }

    public boolean isLocationEnabled() {
        return isLocationEnabled;
    }

    public void setLocationEnabled(boolean locationEnabled) {
        isLocationEnabled = locationEnabled;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
