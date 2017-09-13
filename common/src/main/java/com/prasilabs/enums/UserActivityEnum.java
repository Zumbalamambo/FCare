package com.prasilabs.enums;

/**
 * Created by prasi on 2/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public enum UserActivityEnum {

    BIKING("BIKING"),
    DRIVING("DRIVING"),
    WALKING("WALKING"),
    SLEEPING("SLEEPING"),
    RUNNING("RUNNING"),
    ONLINE("ONLINE"),
    IDLE("IDLE");

    String name;

    UserActivityEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
