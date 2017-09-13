package com.prasilabs.pojos.responsePojos;

import com.prasilabs.pojos.GeoPoint;

/**
 * Created by Contus team on 30/8/17.
 */

public class  UserDataVO {

    private Long id;

    private String userHash;

    private String name;

    private String phone;

    private String userPictureUrl;

    private boolean isVerified;

    private GeoPoint homeLocation;

    private GeoPoint workLocation;

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

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public GeoPoint getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(GeoPoint homeLocation) {
        this.homeLocation = homeLocation;
    }

    public GeoPoint getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(GeoPoint workLocation) {
        this.workLocation = workLocation;
    }
}
