package com.prasilabs.fcare.backend.dbPojos;

import com.prasilabs.enums.UserRelationShipEnum;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by prasi on 2/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */
@Entity
public class UserRelationShipEntity {

    public static final String USER1_ID_STR = "user1Id";

    public static final String USER2_ID_STR = "user2Id";

    @Id
    private Long id;

    @Index
    private long user1Id;

    @Index
    private long user2Id;

    @Index
    private UserRelationShipEnum relationShip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(long user1Id) {
        this.user1Id = user1Id;
    }

    public long getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(long user2Id) {
        this.user2Id = user2Id;
    }

    public UserRelationShipEnum getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(UserRelationShipEnum relationShip) {
        this.relationShip = relationShip;
    }
}
