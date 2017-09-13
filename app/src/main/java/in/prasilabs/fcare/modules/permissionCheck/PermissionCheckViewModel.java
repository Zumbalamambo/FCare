package in.prasilabs.fcare.modules.permissionCheck;

import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;

import in.prasilabs.fcare.utils.LocationUtils;
import in.prasilabs.fcare.utils.PermissionUtils;

/**
 * Created by Contus team on 7/9/17.
 */

public class PermissionCheckViewModel extends CoreViewModel<PermissionCheckCallback> {

    @Override
    protected void onCreateCalled() {

    }

    @Override
    protected void modelEngineUpdated(CoreModelEngine coreModelEngine, Object o) {

    }

    public void checkPermissions() {
        if (getVMCallBack() != null) {
            if (!PermissionUtils.checkMandatoryPermission(getContext())) {
                getVMCallBack().askPermission();
            } else if (!LocationUtils.isLocationEnabled(getContext())) {
                getVMCallBack().askEnableLocation();
            } else {
                getVMCallBack().goToHome();
            }
        }
    }
}
