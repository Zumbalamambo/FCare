package in.prasilabs.fcare.modules.login.userDetails;

import com.prasilabs.pojos.responsePojos.UserDataVO;
import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;

import in.prasilabs.fcare.managers.FUserManager;
import in.prasilabs.fcare.modelEngines.UserModelEngine;

/**
 * Created by Contus team on 30/8/17.
 */

public class UserNameAndPicViewModel extends CoreViewModel<UserNameAndPicViewModelCallback> {

    @Override
    protected void onCreateCalled() {

    }

    @Override
    protected void modelEngineUpdated(CoreModelEngine coreModelEngine, Object o) {

    }

    public void setProfileData(String name, String pictureUrl) {

        if(getVMCallBack() != null) {
            getVMCallBack().showLoading(true);
        }
        UserModelEngine.getInstance().setUserData(name, pictureUrl, new UserModelEngine.SetUserDataCallback() {
            @Override
            public void getUserData(UserDataVO userDataVO, String errorMessage) {
                if(userDataVO != null) {
                    FUserManager.getInstance(getContext()).setUserDataVO(userDataVO);

                    if(getVMCallBack() != null) {
                        getVMCallBack().showLoading(false);
                        getVMCallBack().goToHomeActivity();
                    }
                } else {
                    if(getVMCallBack() != null) {
                        getVMCallBack().showLoading(false);
                        getVMCallBack().showError(errorMessage);
                    }
                }
            }
        });
    }
}
