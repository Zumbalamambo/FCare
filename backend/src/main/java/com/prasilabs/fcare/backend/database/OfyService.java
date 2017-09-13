package com.prasilabs.fcare.backend.database;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.prasilabs.fcare.backend.dbPojos.UserEntity;
import com.prasilabs.fcare.backend.dbPojos.UserLocationStatusEntity;
import com.prasilabs.fcare.backend.dbPojos.UserRelationShipEntity;
import com.prasilabs.fcare.backend.debug.ConsoleLog;

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * Objectify is a Java data access API specifically designed for the Google App Engine datastore.
 * It occupies a "middle ground"; easier to use and more transparent than JDO or JPA, but significantly more convenient than the Low-Level API.
 * Objectify is designed to make novices immediately productive yet also expose the full power of the GAE datastore.
 * More on Objectify here : https://github.com/objectify/objectify
 */
public class OfyService {
    private static final String TAG = OfyService.class.getSimpleName();

    static {
        try {
            ObjectifyService.register(UserEntity.class);
            ObjectifyService.register(UserLocationStatusEntity.class);
            ObjectifyService.register(UserRelationShipEntity.class);
        } catch (Exception e) {
            ConsoleLog.e(e);
            throw e;
        }
    }

    public static Objectify ofy() {
        ObjectifyService.begin();
        return ObjectifyService.ofy();
    }


    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}

