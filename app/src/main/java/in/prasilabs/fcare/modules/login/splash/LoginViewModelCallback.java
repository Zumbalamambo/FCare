package in.prasilabs.fcare.modules.login.splash;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;

/**
 * Created by Contus team on 29/8/17.
 */

public interface LoginViewModelCallback extends CoreCallBack {

    void showLoader(boolean isShow);

    void askUserDetails();

    void goToHome();

    void showLoginFailed();

    void showLoginNeeded();

    void goToPermissionPage();
}
