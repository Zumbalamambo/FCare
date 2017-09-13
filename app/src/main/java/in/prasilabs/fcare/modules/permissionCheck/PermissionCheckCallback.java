package in.prasilabs.fcare.modules.permissionCheck;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;

/**
 * Created by Contus team on 7/9/17.
 */

public interface PermissionCheckCallback extends CoreCallBack {

    void askPermission();

    void goToHome();

    void askEnableLocation();
}
