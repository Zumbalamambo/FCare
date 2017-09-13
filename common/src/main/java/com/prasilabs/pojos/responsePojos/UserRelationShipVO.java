package com.prasilabs.pojos.responsePojos;

import com.prasilabs.CacheData;
import com.prasilabs.enums.UserRelationShipEnum;
/**
 * Created by prasi on 2/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */
public class UserRelationShipVO implements CacheData {

    private Long id;

    private UserDataVO userDataVO;

    private String relationShipEnum;

    private boolean iamActor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDataVO getUserDataVO() {
        return userDataVO;
    }

    public void setUserDataVO(UserDataVO userDataVO) {
        this.userDataVO = userDataVO;
    }

    public String getRelationShipEnum() {
        return relationShipEnum;
    }

    public void setRelationShipEnum(String relationShipEnum) {
        this.relationShipEnum = relationShipEnum;
    }

    public boolean isIamActor() {
        return iamActor;
    }

    public void setIamActor(boolean iamActor) {
        this.iamActor = iamActor;
    }

    @Override
    public String getKey() {
        return String.valueOf(id);
    }
}
