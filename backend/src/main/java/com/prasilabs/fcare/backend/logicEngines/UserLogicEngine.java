package com.prasilabs.fcare.backend.logicEngines;

import com.prasilabs.pojos.responsePojos.UserDataVO;
import com.prasilabs.fcare.backend.database.OfyService;
import com.prasilabs.fcare.backend.dbPojos.UserEntity;
import com.prasilabs.fcare.backend.debug.ConsoleLog;
import com.prasilabs.fcare.backend.security.FaceBookOTPVerifier;
import com.prasilabs.fcare.backend.security.HashGenerator;
import com.prasilabs.fcare.backend.services.utils.OauthUtil;
import com.prasilabs.utils.DataUtil;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

/**
 * Created by Contus team on 30/8/17.
 */

public final class UserLogicEngine {

    private static final String TAG = UserLogicEngine.class.getSimpleName();

    private static UserLogicEngine instance;

    public static UserLogicEngine getInstance() {

        if(instance == null) {
            instance = new UserLogicEngine();
        }

        return instance;
    }

    public UserDataVO verifyOTP(String accessToken) throws OAuthRequestException {

        String number = FaceBookOTPVerifier.verifyOTP(accessToken);

        if(!DataUtil.isEmpty(number)) {
            number = number.replace(" ","");
            number = number.trim();

            UserEntity userEntity = OfyService.ofy().load().type(UserEntity.class).filter(UserEntity.PHONE_STR, number).first().now();

            if(userEntity == null) {
                userEntity = createNewUser(null, number);
            }

            return convertUserEntity(userEntity, true);
        }

        throw new OAuthRequestException("User not found");
    }

    public UserEntity getUserEntity(String userHash) {
        return OfyService.ofy().load().type(UserEntity.class).filter(UserEntity.USER_HASH_STR, userHash).first().now();
    }

    public UserEntity getUserEntityByPhoneNumber(String no) {
        return OfyService.ofy().load().type(UserEntity.class).filter(UserEntity.PHONE_STR, no.trim()).first().now();
    }

    public Key<UserEntity> getUserKey(User user) {
        long userId = OauthUtil.getUserId(user);

        return Key.create(UserEntity.class, userId);
    }

    public UserEntity getUserEntity(User user) {
        long userId = OauthUtil.getUserId(user);

        return OfyService.ofy().load().type(UserEntity.class).id(userId).now();
    }

    public UserEntity getUserEntity(long id) {
        return OfyService.ofy().load().type(UserEntity.class).id(id).now();
    }

    public UserDataVO setNameAndPic(User user, String name, String pic, String deviceId) {

        long userId = OauthUtil.getUserId(user);

        UserEntity userEntity = OfyService.ofy().load().type(UserEntity.class).id(userId).now();

        if(userEntity != null) {
            userEntity.setName(name);
            userEntity.setUserPictureUrl(pic);
            userEntity.setCurrentDeviceId(deviceId);

            OfyService.ofy().save().entity(userEntity).now();
        } else {
            ConsoleLog.w(TAG, "User entity not found for fcare id : " + userId);
        }

        return convertUserEntity(userEntity, true);
    }

    public UserEntity createNewUser(String name, String number) {

        number = number.replace(" ", "");
        number = number.trim();

        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(number);
        userEntity.setName(name);
        userEntity.setUserHash(HashGenerator.sha1(String.valueOf(System.currentTimeMillis())));

        Key<UserEntity> userEntityKey = OfyService.ofy().save().entity(userEntity).now();

        userEntity.setId(userEntityKey.getId());

        return userEntity;
    }

    private static UserDataVO convertUserEntity(UserEntity userEntity, boolean isAddCredentials){

        if(userEntity != null) {
            UserDataVO userDataVO = new UserDataVO();

            userDataVO.setId(userEntity.getId());
            if(isAddCredentials) {
                userDataVO.setUserHash(userEntity.getUserHash());
            }
            userDataVO.setName(userEntity.getName());
            userDataVO.setPhone(userEntity.getPhone());
            userDataVO.setUserPictureUrl(userEntity.getUserPictureUrl());

            return userDataVO;

        }

        return null;
    }

    public UserDataVO getUserDataVo(long user1Id) {

        UserEntity userEntity = OfyService.ofy().load().type(UserEntity.class).id(user1Id).now();

        return convertUserEntity(userEntity, false);
    }

    public UserEntity getUserEntitity(String number) {

        return OfyService.ofy().load().type(UserEntity.class).filter(UserEntity.PHONE_STR, number).first().now();
    }
}
