package in.prasilabs.fcare.internalPojos;

import com.prasilabs.pojos.responsePojos.UserRelationShipVO;

/**
 * Created by Contus team on 7/9/17.
 */

public class RelationShipWithStatusData {
    private Long userId;
    private UserRelationShipVO userRelationShipVO;
    private FirebaseStatusData firebaseStatusData;

    public RelationShipWithStatusData(Long userId, UserRelationShipVO userRelationShipVO, FirebaseStatusData firebaseStatusData) {
        this.userId = userId;
        this.userRelationShipVO = userRelationShipVO;
        this.firebaseStatusData = firebaseStatusData;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserRelationShipVO getUserRelationShipVO() {
        return userRelationShipVO;
    }

    public void setUserRelationShipVO(UserRelationShipVO userRelationShipVO) {
        this.userRelationShipVO = userRelationShipVO;
    }

    public FirebaseStatusData getFirebaseStatusData() {
        return firebaseStatusData;
    }

    public void setFirebaseStatusData(FirebaseStatusData firebaseStatusData) {
        this.firebaseStatusData = firebaseStatusData;
    }
}
