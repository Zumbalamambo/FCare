package in.prasilabs.fcare.modules.careList.viewModel;

import android.support.v4.util.ArrayMap;

import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;
import com.prasilabs.pojos.responsePojos.UserRelationShipVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import in.prasilabs.fcare.internalPojos.FirebaseStatusData;
import in.prasilabs.fcare.internalPojos.RelationShipWithStatusData;
import in.prasilabs.fcare.modelEngines.FirebaseStatusModelEngine;
import in.prasilabs.fcare.modelEngines.FirebaseStatusUploadModelEngine;
import in.prasilabs.fcare.modelEngines.UserRelationshipModelEngine;

/**
 * Created by Contus team on 31/8/17.
 */

public class CareListViewModel extends CoreViewModel<CareListViewModelCallback> {

    private Map<Long, RelationShipWithStatusData> relationShipVOMap = new ArrayMap<>();

    @Override
    protected void onCreateCalled() {
        FirebaseStatusModelEngine.getInstance().addObserver(this);
        FirebaseStatusUploadModelEngine.getInstance().addObserver(this);
    }

    @Override
    protected void modelEngineUpdated(CoreModelEngine coreModelEngine, Object o) {
        if(coreModelEngine instanceof FirebaseStatusModelEngine) {
            if(o != null && o instanceof FirebaseStatusData) {
                RelationShipWithStatusData relationShipVO = relationShipVOMap.get(((FirebaseStatusData) o).getUserId());

                if(relationShipVO != null) {
                    relationShipVO.setFirebaseStatusData((FirebaseStatusData) o);

                    if(getVMCallBack() != null) {
                        getVMCallBack().updateRelation(relationShipVO);
                    }
                }
            }
        } else if(coreModelEngine instanceof FirebaseStatusUploadModelEngine) {
            if(o != null && o instanceof FirebaseStatusData) {
                if(getVMCallBack() != null) {
                    getVMCallBack().updateMyStatus((FirebaseStatusData) o);
                }
            }
        }
    }

    public void addRelation(String name, String number) {
        if(getVMCallBack() != null) {
            getVMCallBack().showProgress(true);
        }

        UserRelationshipModelEngine.getInstance().addRelation(name, number, new UserRelationshipModelEngine.AddRelationShipCallback() {
            @Override
            public void relationAdded(UserRelationShipVO userRelationShipVO) {

                if(getVMCallBack() != null) {
                    getVMCallBack().showProgress(false);
                    getVMCallBack().updateRelation(new RelationShipWithStatusData(userRelationShipVO.getUserDataVO().getId(), userRelationShipVO, null));
                }
            }
        });
    }

    public void getRelations() {
        if(getVMCallBack() != null) {
            getVMCallBack().showProgress(true);
        }

        FirebaseStatusData firebaseStatusData =  FirebaseStatusUploadModelEngine.getInstance().getFirebaseStatusData(getContext());
        if(getVMCallBack() != null) {
            getVMCallBack().updateMyStatus(firebaseStatusData);
        }

        UserRelationshipModelEngine.getInstance().getRelations(true, new UserRelationshipModelEngine.GetRelationShipCallback() {
            @Override
            public void getRelations(List<UserRelationShipVO> userRelationShipVOList) {

                relationShipVOMap.clear();
                List<FirebaseStatusData> firebaseStatusDataList = FirebaseStatusModelEngine.getInstance().getFirebaseStatusList();

                List<RelationShipWithStatusData> relationShipWithStatusDataList = new ArrayList<RelationShipWithStatusData>();

                if(userRelationShipVOList != null) {
                    for(UserRelationShipVO userRelationShipVO : userRelationShipVOList) {

                        RelationShipWithStatusData relationShipWithStatusData = new RelationShipWithStatusData(userRelationShipVO.getUserDataVO().getId(), userRelationShipVO, null);
                        relationShipVOMap.put(relationShipWithStatusData.getUserId(), relationShipWithStatusData);

                        for(FirebaseStatusData firebaseStatusData : firebaseStatusDataList) {
                            if(Objects.equals(firebaseStatusData.getUserId(), relationShipWithStatusData.getUserId())) {
                                relationShipWithStatusData.setFirebaseStatusData(firebaseStatusData);
                                break;
                            }
                        }

                        relationShipWithStatusDataList.add(relationShipWithStatusData);
                    }
                }

                if(getVMCallBack() != null) {
                    getVMCallBack().showProgress(false);
                    getVMCallBack().showRelations(relationShipWithStatusDataList);
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FirebaseStatusModelEngine.getInstance().deleteObserver(this);
    }

    public void acceptUser(final long userId, boolean isAccept) {
        if(getVMCallBack() != null) {
            getVMCallBack().showProgress(true);
        }
        UserRelationshipModelEngine.getInstance().acceptUser(userId, isAccept, new UserRelationshipModelEngine.ConfirmRelationCallback() {
            @Override
            public void confirmed(UserRelationShipVO userRelationShipVO) {

                if(getVMCallBack() != null) {
                    getVMCallBack().showProgress(false);
                }

                if(userRelationShipVO != null) {
                    RelationShipWithStatusData relationShipWithStatusData = new RelationShipWithStatusData(userRelationShipVO.getUserDataVO().getId(), userRelationShipVO, null);
                    relationShipVOMap.put(relationShipWithStatusData.getUserId(), relationShipWithStatusData);

                    if(getVMCallBack() != null) {
                        getVMCallBack().updateRelation(relationShipWithStatusData);
                    }
                }
            }
        });
    }
}
