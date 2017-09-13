package in.prasilabs.fcare.modules.permissionCheck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;

import butterknife.OnClick;
import in.prasilabs.fcare.R;
import in.prasilabs.fcare.base.BaseActivity;
import in.prasilabs.fcare.modules.home.HomeActivity;
import in.prasilabs.fcare.utils.LocationUtils;
import in.prasilabs.fcare.utils.PermissionUtils;

public class PermissionCheckActivity extends BaseActivity<PermissionCheckViewModel> implements PermissionCheckCallback {

    private static final int REQ_FOR_PERMISSION = 12;
    private static final int REQ_FOR_LOCATION = 10;

    public static void openPermissionCheckActivity(Context context) {
        Intent intent = new Intent(context, PermissionCheckActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_check);
    }

    @Override
    protected CoreCallBack getCoreCallBack() {
        return this;
    }

    @Override
    protected PermissionCheckViewModel setCoreViewModel() {
        return new PermissionCheckViewModel();
    }

    @OnClick(R.id.next_btn)
    void onNextClicked() {
        getViewModel().checkPermissions();
    }

    @Override
    public void askPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.READ_PHONE_STATE}, REQ_FOR_PERMISSION);
    }

    @Override
    public void goToHome() {
        HomeActivity.openHomeActivity(this);
        finish();
    }

    @Override
    public void askEnableLocation() {
        LocationUtils.askEnableLocationRequest(this, REQ_FOR_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQ_FOR_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getViewModel().checkPermissions();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_FOR_LOCATION && resultCode == RESULT_OK) {
            getViewModel().checkPermissions();
        }
    }
}
