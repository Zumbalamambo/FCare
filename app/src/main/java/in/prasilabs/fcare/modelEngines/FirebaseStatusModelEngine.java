package in.prasilabs.fcare.modelEngines;

import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;

import java.util.List;

import in.prasilabs.fcare.internalPojos.FirebaseStatusData;
import in.prasilabs.fcare.services.cacheService.ListCache;

/**
 * Created by Contus team on 7/9/17.
 */

public class FirebaseStatusModelEngine extends CoreModelEngine {

    ListCache<FirebaseStatusData> firebaseStatusDataListCache = new ListCache<>();

    private static FirebaseStatusModelEngine instance;

    public static FirebaseStatusModelEngine getInstance() {
        if(instance == null) {
            instance = new FirebaseStatusModelEngine();
        }
        return instance;
    }


    public void triggerDataChange(FirebaseStatusData firebaseStatusData) {
        firebaseStatusDataListCache.updateCacheList(firebaseStatusData);

        setChanged();
        notifyObservers(firebaseStatusData);
    }

    public List<FirebaseStatusData> getFirebaseStatusList() {
        return firebaseStatusDataListCache.getCacheList();
    }
}
