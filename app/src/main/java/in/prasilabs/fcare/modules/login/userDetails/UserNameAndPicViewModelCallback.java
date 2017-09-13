package in.prasilabs.fcare.modules.login.userDetails;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;

/**
 * Created by Contus team on 30/8/17.
 */

public interface UserNameAndPicViewModelCallback extends CoreCallBack {

    void goToHomeActivity();

    void showError(String erroMessage);

    void showLoading(boolean isShow);
}
