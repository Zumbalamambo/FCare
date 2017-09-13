package com.prasilabs.fcare.backend.logicEngines;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.prasilabs.fcare.backend.core.CoreLogicEngine;
import com.prasilabs.fcare.backend.database.OfyService;
import com.prasilabs.fcare.backend.dbPojos.UserEntity;
import com.prasilabs.fcare.backend.dbPojos.UserLocationStatusEntity;
import com.prasilabs.fcare.backend.utils.DataUtil;
import com.prasilabs.pojos.GeoPoint;
import com.prasilabs.pojos.responsePojos.ApiResponseVO;

/**
 * Created by prasi on 4/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class StatusLogicEngine extends CoreLogicEngine {

    private static StatusLogicEngine instance;

    public static StatusLogicEngine getInstance() {

        if(instance == null) {
            instance = new StatusLogicEngine();
        }

        return instance;
    }

    private StatusLogicEngine(){}

    public ApiResponseVO uploadLocationStatus(User user, GeoPoint geoPoint, long timeInMillis, boolean isLocationEnabled) {
        ApiResponseVO apiResponseVO = new ApiResponseVO();

        Key<UserEntity> userEntityKey = UserLogicEngine.getInstance().getUserKey(user);

        UserLocationStatusEntity userLocationStatusEntity = new UserLocationStatusEntity();
        userLocationStatusEntity.setUserEntityKey(userEntityKey);
        userLocationStatusEntity.setLocation(DataUtil.convertGeoPoint(geoPoint));
        userLocationStatusEntity.setLocationEnabled(isLocationEnabled);
        userLocationStatusEntity.setLastUpdateTime(timeInMillis);

        Key<UserLocationStatusEntity> userLocationStatusEntityKey = OfyService.ofy().save().entity(userLocationStatusEntity).now();

        apiResponseVO.setSuccess(true);
        apiResponseVO.setId(userLocationStatusEntityKey.getId());

        return apiResponseVO;
    }
}
