package in.prasilabs.fcare.modules.careList.viewModel;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;
import com.prasilabs.pojos.responsePojos.UserRelationShipVO;

import java.util.List;

import in.prasilabs.fcare.internalPojos.FirebaseStatusData;
import in.prasilabs.fcare.internalPojos.RelationShipWithStatusData;

/**
 * Created by Contus team on 31/8/17.
 */

public interface CareListViewModelCallback extends CoreCallBack {

    void showProgress(boolean isShow);

    void showRelations(List<RelationShipWithStatusData> relationShipWithStatusDataList);

    void updateRelation(RelationShipWithStatusData relationShipWithStatusData);

    void updateMyStatus(FirebaseStatusData firebaseStatusData);
}
