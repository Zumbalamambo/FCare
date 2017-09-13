package in.prasilabs.fcare.internalPojos;

/**
 * Created by prasi on 7/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FirebaseBatteryStatus {
    private int percentage;
    private boolean charging;
    private long updatedTimeInMillis;

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public long getUpdatedTimeInMillis() {
        return updatedTimeInMillis;
    }

    public void setUpdatedTimeInMillis(long updatedTimeInMillis) {
        this.updatedTimeInMillis = updatedTimeInMillis;
    }
}
