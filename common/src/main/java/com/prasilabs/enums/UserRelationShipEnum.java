package com.prasilabs.enums;

/**
 * Created by prasi on 2/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public enum UserRelationShipEnum {

    CONNECTED("CONNECTED"),
    REQUESTED("REQUESTED"),
    BLOCKED("BLOCKED");

    private String name;

    UserRelationShipEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
