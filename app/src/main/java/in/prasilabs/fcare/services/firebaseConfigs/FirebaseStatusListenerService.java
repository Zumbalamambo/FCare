package in.prasilabs.fcare.services.firebaseConfigs;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.prasilabs.enums.UserRelationShipEnum;
import com.prasilabs.pojos.responsePojos.UserRelationShipVO;
import com.prasilabs.utils.DataUtil;

import java.util.List;
import java.util.Map;

import in.prasilabs.fcare.internalPojos.FirebaseStatusData;
import in.prasilabs.fcare.modelEngines.FirebaseStatusModelEngine;
import in.prasilabs.fcare.modelEngines.UserRelationshipModelEngine;

/**
 * Created by Contus team on 7/9/17.
 */

public class FirebaseStatusListenerService extends Service implements ValueEventListener {

    private Map<Long, DatabaseReference> databaseReferenceMap = new ArrayMap<>();

    public static void startFirebaseService(Context context) {
        Intent intent = new Intent(context, FirebaseStatusListenerService.class);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        listen();

        return START_STICKY;
    }

    public void listen() {

        clearListeners();

        UserRelationshipModelEngine.getInstance().getRelations(false, new UserRelationshipModelEngine.GetRelationShipCallback() {
            @Override
            public void getRelations(List<UserRelationShipVO> userRelationShipVOList) {
                if(userRelationShipVOList != null) {
                    listenToUsers(userRelationShipVOList);
                }
            }
        });
    }

    private void listenToUsers(List<UserRelationShipVO> userRelationShipVOList) {

        for(UserRelationShipVO userRelationShipVO : userRelationShipVOList) {
            if(userRelationShipVO.getRelationShipEnum().equals(UserRelationShipEnum.CONNECTED.getName())) {
                DatabaseReference databaseReference = FireBaseConfig.getFirebaseDatabase(this).getReference(String.valueOf(userRelationShipVO.getUserDataVO().getId()));
                databaseReference.addValueEventListener(this);
                databaseReferenceMap.put(userRelationShipVO.getUserDataVO().getId(), databaseReference);
            }
        }
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        FirebaseStatusData firebaseStatusData = dataSnapshot.getValue(FirebaseStatusData.class);
        if(firebaseStatusData != null) {
            firebaseStatusData.setUserId(DataUtil.stringToLong(dataSnapshot.getKey()));

            FirebaseStatusModelEngine.getInstance().triggerDataChange(firebaseStatusData);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        ConsoleLog.e(databaseError.toException());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearListeners();
    }

    private void clearListeners() {
        for (Map.Entry<Long, DatabaseReference> entry : databaseReferenceMap.entrySet())
        {
            entry.getValue().removeEventListener(this);
        }
        databaseReferenceMap.clear();
    }
}
