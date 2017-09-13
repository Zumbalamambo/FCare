/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.prasilabs.fcare.backend.endPoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.prasilabs.fcare.backend.debug.ConsoleLog;
import com.prasilabs.fcare.backend.logicEngines.StatusLogicEngine;
import com.prasilabs.fcare.backend.security.HashAuthenticator;
import com.prasilabs.fcare.backend.services.utils.OauthUtil;
import com.prasilabs.pojos.GeoPoint;
import com.prasilabs.pojos.responsePojos.ApiResponseVO;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "statusApi",
        version = "v1",
        authenticators = {HashAuthenticator.class}, //add EndpointsAuthenticator to the end of the @authenticators
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.fcare.example.com",
                ownerName = "backend.myapplication.fcare.example.com",
                packagePath = ""
        )
)
public class StatusEndpoint {

    @ApiMethod(name = "uploadLocation", httpMethod = ApiMethod.HttpMethod.POST)
    public ApiResponseVO uploadLocationStatus(User user, @Named("isLocationEnabled") boolean isLocationEnabled, @Named("timeInMillis") long timeInMillis, GeoPoint geoPoint) throws OAuthRequestException {

        try {
            OauthUtil.checkAndThrow(user);

            return StatusLogicEngine.getInstance().uploadLocationStatus(user, geoPoint, timeInMillis, isLocationEnabled);
        }catch (Exception e){
            ConsoleLog.e(e);

            throw e;
        }
    }
}
