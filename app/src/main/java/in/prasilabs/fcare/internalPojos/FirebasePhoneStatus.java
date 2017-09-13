package in.prasilabs.fcare.internalPojos;

/**
 * Created by prasi on 8/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FirebasePhoneStatus {

    private boolean screenOn;

    private long updatedTimeInMillis;

    public boolean isScreenOn() {
        return screenOn;
    }

    public void setScreenOn(boolean screenOn) {
        this.screenOn = screenOn;
    }

    public long getUpdatedTimeInMillis() {
        return updatedTimeInMillis;
    }

    public void setUpdatedTimeInMillis(long updatedTimeInMillis) {
        this.updatedTimeInMillis = updatedTimeInMillis;
    }
}
