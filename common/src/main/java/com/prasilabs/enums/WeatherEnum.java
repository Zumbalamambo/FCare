package com.prasilabs.enums;

/**
 * Created by prasi on 2/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public enum WeatherEnum {

    CLEAR_DAY("CLEAR_DAY"),
    cLEAR_NIGHT("CLEAR_NIGHT"),
    PARTLY_CLOUDY_DAY("PARTLY_CLOUDY_DAY"),
    PARTLY_CLOUDY_NIGHT("PARTLY_CLOUDY_NIGHT"),
    CLOUDY("CLOUDY"),
    RAINY("RAINY"),
    SLEET("SLEET"),
    SNOW("SNOW"),
    WIND("WIND"),
    FOG("FOG");

    private String name;

    WeatherEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
