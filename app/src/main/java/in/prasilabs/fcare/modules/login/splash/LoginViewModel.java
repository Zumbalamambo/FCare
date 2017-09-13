package in.prasilabs.fcare.modules.login.splash;

import android.text.TextUtils;

import com.prasilabs.pojos.responsePojos.UserDataVO;
import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;

import in.prasilabs.fcare.managers.FUserManager;
import in.prasilabs.fcare.modelEngines.UserModelEngine;
import in.prasilabs.fcare.utils.PermissionUtils;

/**
 * Created by Contus team on 29/8/17.
 */

public class LoginViewModel extends CoreViewModel<LoginViewModelCallback> {

    @Override
    protected void onCreateCalled() {

    }

    @Override
    protected void modelEngineUpdated(CoreModelEngine coreModelEngine, Object o) {

    }

    public void checkForExistingUser() {

        if(getVMCallBack() != null) {
            UserDataVO userDataVO = FUserManager.getInstance(getContext()).getUserDataVO();

            checkAndProceed(userDataVO);
        }
    }

    public void login(String accessToken) {

        if(getVMCallBack() != null) {
            getVMCallBack().showLoader(true);
        }

        UserModelEngine.getInstance().verifyOTP(accessToken, new UserModelEngine.OtpCallback() {
            @Override
            public void getUserData(UserDataVO userDataVO, String errorMessage) {

                if(userDataVO != null) {
                    FUserManager.getInstance(getContext()).setUserDataVO(userDataVO);

                    if(getVMCallBack() != null) {
                        getVMCallBack().showLoader(false);
                        checkAndProceed(userDataVO);
                    }
                } else {
                    if(getVMCallBack() != null) {
                        getVMCallBack().showLoginFailed();
                    }
                }
            }
        });
    }

    private void checkAndProceed(UserDataVO userDataVO) {
        if(userDataVO != null && !TextUtils.isEmpty(userDataVO.getUserHash())) {

            if(TextUtils.isEmpty(userDataVO.getName())) {
                getVMCallBack().askUserDetails();
            } else {
                if(PermissionUtils.checkMandatoryPermission(getContext())) {
                    getVMCallBack().goToHome();
                } else {
                    getVMCallBack().goToPermissionPage();
                }
            }
        } else {
            getVMCallBack().showLoginNeeded();
        }
    }


}
