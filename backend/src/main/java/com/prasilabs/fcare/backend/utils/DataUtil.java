package com.prasilabs.fcare.backend.utils;

import com.google.appengine.api.datastore.GeoPt;
import com.prasilabs.pojos.GeoPoint;

/**
 * Created by prasi on 4/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class DataUtil {

    private DataUtil(){}

    public static GeoPt convertGeoPoint(GeoPoint geoPoint) {
        Double lat = geoPoint.getLat();
        Double lon = geoPoint.getLon();

        return new GeoPt(lat.floatValue(), lon.floatValue());
    }

}
