package com.prasilabs.fcare.backend.dbPojos;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Contus team on 29/8/17.
 */

@Entity
public class UserEntity {

    public static final String USER_HASH_STR = "userHash";
    public static final String PHONE_STR = "phone";

    @Id
    private Long id;

    @Index
    private String userHash;

    private String name;

    @Index
    private String phone;

    private String userPictureUrl;

    @Index
    private String currentDeviceId;

    @Index
    private GeoPt homeLocation;

    @Index
    private GeoPt workLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    public void setUserPictureUrl(String userPictureUrl) {
        this.userPictureUrl = userPictureUrl;
    }

    public String getCurrentDeviceId() {
        return currentDeviceId;
    }

    public void setCurrentDeviceId(String currentDeviceId) {
        this.currentDeviceId = currentDeviceId;
    }

    public GeoPt getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(GeoPt homeLocation) {
        this.homeLocation = homeLocation;
    }

    public GeoPt getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(GeoPt workLocation) {
        this.workLocation = workLocation;
    }
}
