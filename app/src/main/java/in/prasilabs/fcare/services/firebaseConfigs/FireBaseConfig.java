package in.prasilabs.fcare.services.firebaseConfigs;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by prasi on 13/2/17.
 */

public class FireBaseConfig
{
    private static final FireBaseConfig instance = new FireBaseConfig();
    private static FirebaseDatabase firebaseDatabase;
    private FirebaseApp firebaseApp;

    private static FirebaseApp getFirebaseApp(Context context)
    {
        if(instance.firebaseApp == null)
        {
            instance.firebaseApp = FirebaseApp.initializeApp(context);
        }
        return instance.firebaseApp;
    }

    public static FirebaseDatabase getFirebaseDatabase(Context context)
    {
        if(firebaseDatabase == null)
        {
            firebaseDatabase = FirebaseDatabase.getInstance(getFirebaseApp(context));
        }
        return firebaseDatabase;
    }
}
