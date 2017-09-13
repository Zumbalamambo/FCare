/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.prasilabs.fcare.backend.endPoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.prasilabs.fcare.backend.debug.ConsoleLog;
import com.prasilabs.fcare.backend.logicEngines.UserLogicEngine;
import com.prasilabs.fcare.backend.logicEngines.UserRelationLogicEngine;
import com.prasilabs.fcare.backend.security.HashAuthenticator;
import com.prasilabs.fcare.backend.services.utils.OauthUtil;
import com.prasilabs.pojos.responsePojos.UserDataVO;
import com.prasilabs.pojos.responsePojos.UserRelationShipVO;
import com.prasilabs.pojos.responsePojos.UserRelationShipVOCollection;

import java.util.List;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "userApi",
        version = "v1",
        authenticators = {HashAuthenticator.class}, //add EndpointsAuthenticator to the end of the @authenticators
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.fcare.example.com",
                ownerName = "backend.myapplication.fcare.example.com",
                packagePath = ""
        )
)
public class UserEndpoint {

    @ApiMethod(name = "verifyOTP")
    public UserDataVO verifyOTP(@Named("access_token") String accessToken) throws OAuthRequestException {

        try {
            return UserLogicEngine.getInstance().verifyOTP(accessToken);
        } catch (Exception e) {

            ConsoleLog.e(e);

            throw e;
        }
    }

    @ApiMethod(name = "setUserDetails")
    public UserDataVO setUserDetails(User user, @Named("name") String name, @Nullable @Named("picture") String picture, @Named("device_id") String deviceId) throws OAuthRequestException {

        try {
            OauthUtil.checkAndThrow(user);
            return UserLogicEngine.getInstance().setNameAndPic(user, name, picture, deviceId);
        } catch (Exception e) {
            ConsoleLog.e(e);

            throw e;
        }
    }

    @ApiMethod(name = "getUserRelation", httpMethod = ApiMethod.HttpMethod.GET)
    public UserRelationShipVOCollection getUserRelation(User user) throws OAuthRequestException {
        try {
            OauthUtil.checkAndThrow(user);

            return UserRelationLogicEngine.getInstance().getUserRelations(user);
        }catch (Exception e) {
            ConsoleLog.e(e);

            throw e;
        }
    }

    @ApiMethod(name = "addUserRelation")
    public UserRelationShipVO addUserRelation(User user, @Nullable @Named("name") String name, @Named("number") String number) throws OAuthRequestException {
        try {
            OauthUtil.checkAndThrow(user);

            return UserRelationLogicEngine.getInstance().addUserRelation(user, name, number);
        }catch (Exception e) {
            ConsoleLog.e(e);

            throw e;
        }
    }

    @ApiMethod(name = "confirmUserRelation", httpMethod = ApiMethod.HttpMethod.PUT)
    public UserRelationShipVO confirmUserRelation(User user, @Named("user2Id") long user2Id, @Named("isAccept") boolean isAccept) throws OAuthRequestException, NotFoundException {
        try {
            OauthUtil.checkAndThrow(user);
            return UserRelationLogicEngine.getInstance().confirmUserRelation(user, user2Id, isAccept);
        }catch (Exception e) {
            ConsoleLog.e(e);

            throw e;
        }
    }
}
