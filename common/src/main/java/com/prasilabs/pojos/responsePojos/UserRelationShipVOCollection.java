package com.prasilabs.pojos.responsePojos;

import java.util.List;

/**
 * Created by prasi on 8/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class UserRelationShipVOCollection {
    private List<UserRelationShipVO> userRelationShipVOList;

    public List<UserRelationShipVO> getUserRelationShipVOList() {
        return userRelationShipVOList;
    }

    public void setUserRelationShipVOList(List<UserRelationShipVO> userRelationShipVOList) {
        this.userRelationShipVOList = userRelationShipVOList;
    }
}
