package in.prasilabs.fcare.modelEngines;

import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.prasilabs.pojos.responsePojos.UserDataVO;
import com.prasilabs.pojos.responsePojos.UserRelationShipVO;
import com.prasilabs.pojos.responsePojos.UserRelationShipVOCollection;

import java.util.ArrayList;
import java.util.List;

import in.prasilabs.fcare.base.BaseApp;
import in.prasilabs.fcare.services.api.ApiManager;
import in.prasilabs.fcare.services.api.FCallBack;
import in.prasilabs.fcare.services.cacheService.ListCache;
import in.prasilabs.fcare.services.firebaseConfigs.FirebaseStatusListenerService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Contus team on 6/9/17.
 */

public class UserRelationshipModelEngine extends CoreModelEngine {

    private ListCache<UserRelationShipVO> userRelationShipVOListCache = new ListCache<>();

    private static UserRelationshipModelEngine instance;

    public static UserRelationshipModelEngine getInstance() {
        if(instance == null) {
            instance = new UserRelationshipModelEngine();
        }
        return instance;
    }

    public void addRelation(String name, final String number, final AddRelationShipCallback addRelationShipCallback) {

        final Call<UserRelationShipVO> relationShipCall = ApiManager.getInstance().getUserRelationRequest().addRelation(number, name);
        relationShipCall.enqueue(new FCallBack<UserRelationShipVO>() {
            @Override
            public void onResponse(Call<UserRelationShipVO> call, Response<UserRelationShipVO> response) {

                UserRelationShipVO userRelationShipVO = response.body();

                userRelationShipVOListCache.updateCacheList(userRelationShipVO);

                if(addRelationShipCallback != null) {
                    addRelationShipCallback.relationAdded(userRelationShipVO);
                }
            }

            @Override
            public void onFailure(Call<UserRelationShipVO> call, Throwable t) {

                userRelationShipVOListCache.setCacheList(null);

                if(addRelationShipCallback != null) {
                    addRelationShipCallback.relationAdded(null);
                }
            }
        });
    }

    public void getRelations(boolean isHardRefresh, final GetRelationShipCallback getRelationShipCallback) {

        if(!isHardRefresh && userRelationShipVOListCache.isCacheDataPresent()) {
            if(getRelationShipCallback != null) {
                getRelationShipCallback.getRelations(userRelationShipVOListCache.getCacheList());
            }
        }else {
            final Call<UserRelationShipVOCollection> relationShipCall = ApiManager.getInstance().getUserRelationRequest().getRelations();
            relationShipCall.enqueue(new FCallBack<UserRelationShipVOCollection>() {
                @Override
                public void onResponse(Call<UserRelationShipVOCollection> call, Response<UserRelationShipVOCollection> response) {

                    UserRelationShipVOCollection userRelationShipVOCollection = response.body();

                    if(userRelationShipVOCollection != null && userRelationShipVOCollection.getUserRelationShipVOList() != null) {

                        userRelationShipVOListCache.setCacheList(userRelationShipVOCollection.getUserRelationShipVOList());
                        if(getRelationShipCallback != null) {
                            getRelationShipCallback.getRelations(userRelationShipVOCollection.getUserRelationShipVOList());
                        }
                    } else {
                        if(getRelationShipCallback != null) {
                            getRelationShipCallback.getRelations(null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserRelationShipVOCollection> call, Throwable t) {

                    userRelationShipVOListCache.setCacheList(null);

                    if(getRelationShipCallback != null) {
                        getRelationShipCallback.getRelations(null);
                    }
                }
            });
        }
    }

    public void acceptUser(long userId, boolean isAccept, final ConfirmRelationCallback confirmRelationCallback) {
        final Call<UserRelationShipVO> relationShipCall = ApiManager.getInstance().getUserRelationRequest().acceptRelation(userId, isAccept);
        relationShipCall.enqueue(new FCallBack<UserRelationShipVO>() {
            @Override
            public void onResponse(Call<UserRelationShipVO> call, Response<UserRelationShipVO> response) {

                UserRelationShipVO userRelationShipVO = response.body();

                userRelationShipVOListCache.updateCacheList(userRelationShipVO);
                FirebaseStatusListenerService.startFirebaseService(BaseApp.getAppContext());

                if(confirmRelationCallback != null) {
                    confirmRelationCallback.confirmed(userRelationShipVO);
                }
            }

            @Override
            public void onFailure(Call<UserRelationShipVO> call, Throwable t) {

                userRelationShipVOListCache.setCacheList(null);

                if(confirmRelationCallback != null) {
                    confirmRelationCallback.confirmed(null);
                }
            }
        });
    }

    public interface GetRelationShipCallback {

        void getRelations(List<UserRelationShipVO> userRelationShipVOList);
    }

    public interface AddRelationShipCallback {
        void relationAdded(UserRelationShipVO userRelationShipVO);
    }

    public interface ConfirmRelationCallback {
        void confirmed(UserRelationShipVO userRelationShipVO);
    }
}
