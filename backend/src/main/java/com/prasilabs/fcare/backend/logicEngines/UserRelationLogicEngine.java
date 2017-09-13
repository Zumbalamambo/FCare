package com.prasilabs.fcare.backend.logicEngines;

import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.prasilabs.enums.UserRelationShipEnum;
import com.prasilabs.fcare.backend.core.CoreLogicEngine;
import com.prasilabs.fcare.backend.database.OfyService;
import com.prasilabs.fcare.backend.dbPojos.UserEntity;
import com.prasilabs.fcare.backend.dbPojos.UserRelationShipEntity;
import com.prasilabs.fcare.backend.debug.ConsoleLog;
import com.prasilabs.fcare.backend.services.utils.OauthUtil;
import com.prasilabs.pojos.responsePojos.UserDataVO;
import com.prasilabs.pojos.responsePojos.UserRelationShipVO;
import com.prasilabs.pojos.responsePojos.UserRelationShipVOCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Contus team on 6/9/17.
 */

public final class UserRelationLogicEngine extends CoreLogicEngine {

    private static final String TAG = UserRelationLogicEngine.class.getSimpleName();

    private static UserRelationLogicEngine instance;

    private UserRelationLogicEngine(){}

    public static UserRelationLogicEngine getInstance() {
        if(instance == null) {
            instance = new UserRelationLogicEngine();
        }
        return instance;
    }

    public UserRelationShipVOCollection getUserRelations(User user) {
        long userId = OauthUtil.getUserId(user);

        List<UserRelationShipVO> userRelationShipVOList = new ArrayList<>();

        Query.Filter user1Filter = new Query.FilterPredicate(UserRelationShipEntity.USER1_ID_STR, Query.FilterOperator.EQUAL, userId);
        Query.Filter user2Filter = new Query.FilterPredicate(UserRelationShipEntity.USER2_ID_STR, Query.FilterOperator.EQUAL, userId);

        Query.Filter filter = Query.CompositeFilterOperator.or(user1Filter, user2Filter);
        ConsoleLog.i(TAG, filter.toString());

        List<UserRelationShipEntity> userRelationShipEntityList = OfyService.ofy().load().type(UserRelationShipEntity.class).filter(filter).list();

        for(UserRelationShipEntity userRelationShipEntity : userRelationShipEntityList) {
            userRelationShipVOList.add(convertUserRelationShipVO(userRelationShipEntity, userId));
        }

        UserRelationShipVOCollection userRelationShipVOCollection = new UserRelationShipVOCollection();
        userRelationShipVOCollection.setUserRelationShipVOList(userRelationShipVOList);

        return userRelationShipVOCollection;
    }

    private static UserRelationShipVO convertUserRelationShipVO(UserRelationShipEntity userRelationShipEntity, long userId) {

        UserRelationShipVO userRelationShipVO = new UserRelationShipVO();

        userRelationShipVO.setId(userRelationShipEntity.getId());
        if(userRelationShipEntity.getUser1Id() == userId) {
            userRelationShipVO.setIamActor(false);
            userRelationShipVO.setUserDataVO(UserLogicEngine.getInstance().getUserDataVo(userRelationShipEntity.getUser2Id()));
        } else {
            userRelationShipVO.setIamActor(true);
            userRelationShipVO.setUserDataVO(UserLogicEngine.getInstance().getUserDataVo(userRelationShipEntity.getUser1Id()));
        }
        userRelationShipVO.setRelationShipEnum(userRelationShipEntity.getRelationShip().getName());

        return userRelationShipVO;
    }

    private UserRelationShipEntity getUserRelationShip(long user1Id, long user2Id) {

        Query.Filter user1IamFilter = new Query.FilterPredicate(UserRelationShipEntity.USER1_ID_STR, Query.FilterOperator.EQUAL, user1Id);
        Query.Filter user2HeFilter = new Query.FilterPredicate(UserRelationShipEntity.USER2_ID_STR, Query.FilterOperator.EQUAL, user2Id);

        Query.Filter user1HeFilter = new Query.FilterPredicate(UserRelationShipEntity.USER1_ID_STR, Query.FilterOperator.EQUAL, user2Id);
        Query.Filter user2IamFilter = new Query.FilterPredicate(UserRelationShipEntity.USER2_ID_STR, Query.FilterOperator.EQUAL, user1Id);

        Query.Filter filter1 = Query.CompositeFilterOperator.and(user1IamFilter, user2HeFilter);
        Query.Filter filter2 = Query.CompositeFilterOperator.and(user1HeFilter, user2IamFilter);

        Query.Filter filter = Query.CompositeFilterOperator.or(filter1, filter2);
        ConsoleLog.i(TAG, filter.toString());

        return OfyService.ofy().load().type(UserRelationShipEntity.class).filter(filter).first().now();
    }

    public UserRelationShipVO addUserRelation(User user, String name, String number) {

        long userId = OauthUtil.getUserId(user);
        number = number.replace(" ","");
        number = number.trim();

        UserEntity userEntity = UserLogicEngine.getInstance().getUserEntityByPhoneNumber(number);

        if(userEntity == null) {
            userEntity = UserLogicEngine.getInstance().createNewUser(name, number);
        }

        UserRelationShipEntity userRelationShipEntity = getUserRelationShip(userId, userEntity.getId());

        if(userRelationShipEntity == null) {
            userRelationShipEntity = new UserRelationShipEntity();
            userRelationShipEntity.setUser1Id(userId);
            userRelationShipEntity.setUser2Id(userEntity.getId());
            userRelationShipEntity.setRelationShip(UserRelationShipEnum.REQUESTED);

            Key<UserRelationShipEntity> userRelationShipEntityKey = OfyService.ofy().save().entity(userRelationShipEntity).now();
            userRelationShipEntity.setId(userRelationShipEntityKey.getId());
        }


        return convertUserRelationShipVO(userRelationShipEntity, userId);
    }

    public UserRelationShipVO confirmUserRelation(User user, long hisId, boolean isAccept) throws NotFoundException {

        long myId = OauthUtil.getUserId(user);

        UserRelationShipEntity userRelationShipEntity = getUserRelationShip(myId, hisId);

        if(userRelationShipEntity != null) {

            if(userRelationShipEntity.getUser2Id() == myId && userRelationShipEntity.getRelationShip() == UserRelationShipEnum.REQUESTED) {
                if(isAccept) {
                    userRelationShipEntity.setRelationShip(UserRelationShipEnum.CONNECTED);
                } else {
                    userRelationShipEntity.setUser1Id(myId);
                    userRelationShipEntity.setUser2Id(hisId);
                    userRelationShipEntity.setRelationShip(UserRelationShipEnum.BLOCKED);
                }
                OfyService.ofy().save().entity(userRelationShipEntity).now();

                return convertUserRelationShipVO(userRelationShipEntity, myId);
            } else {
                throw new NotFoundException("Relationship cannot do this. Security");
            }
        } else {
            throw new NotFoundException("relation ship not found");
        }
    }
}
