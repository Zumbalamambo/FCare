package in.prasilabs.fcare.services.call;

import android.content.Context;

import java.util.Date;

/**
 * Created by prasi on 1/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class FCallReceiver extends FCallAbstractReceiver {

    @Override
    protected void onIncomingCallReceived(Context ctx, String number, Date start) {

    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {

    }
}
