package in.prasilabs.fcare.internalPojos;

/**
 * Created by prasi on 7/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FirebaseActivityStatus {
    private String userActivityEnum;
    private long updatedTimeInMillis;

    public String getUserActivityEnum() {
        return userActivityEnum;
    }

    public void setUserActivityEnum(String userActivityEnum) {
        this.userActivityEnum = userActivityEnum;
    }

    public long getUpdatedTimeInMillis() {
        return updatedTimeInMillis;
    }

    public void setUpdatedTimeInMillis(long updatedTimeInMillis) {
        this.updatedTimeInMillis = updatedTimeInMillis;
    }
}
