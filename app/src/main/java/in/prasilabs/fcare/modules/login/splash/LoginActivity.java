package in.prasilabs.fcare.modules.login.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.prasilabs.droidwizardlib.utils.FragmentNavigator;

import butterknife.BindView;
import butterknife.OnClick;
import in.prasilabs.fcare.R;
import in.prasilabs.fcare.base.BaseActivity;
import in.prasilabs.fcare.modules.home.HomeActivity;
import in.prasilabs.fcare.modules.login.userDetails.UserNameAndPicFragment;
import in.prasilabs.fcare.modules.permissionCheck.PermissionCheckActivity;
import in.prasilabs.fcare.utils.ViewUtil;

public class LoginActivity extends BaseActivity<LoginViewModel> implements LoginViewModelCallback {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int APP_REQUEST_CODE = 12;

    private static final int APP_PERMISSION_CODE = 10;

    @BindView(R.id.pb_login)
    ProgressBar loginProgress;

    @BindView(R.id.tv_phone)
    Button phoneLoginButton;

    @BindView(R.id.container)
    LinearLayout containerLayout;

    @BindView(R.id.vp_login)
    ViewPager loginViewPager;

    SplashViewPagerAdapter splashViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        splashViewPagerAdapter = new SplashViewPagerAdapter();

        phoneLoginButton.setVisibility(View.VISIBLE);
        loginProgress.setVisibility(View.GONE);
        loginViewPager.setAdapter(splashViewPagerAdapter);

        getViewModel().checkForExistingUser();

        handleViewPagerAnim();
    }

    void handleViewPagerAnim() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isDestroyed()) {
                    int currentPos = loginViewPager.getCurrentItem();

                    if(currentPos >= splashViewPagerAdapter.getCount()-1) {
                        currentPos = 0;
                    } else {
                        currentPos++;
                    }

                    loginViewPager.setCurrentItem(currentPos);

                    handleViewPagerAnim();
                }
            }
        },2500);
    }

    @Override
    protected CoreCallBack getCoreCallBack() {
        return this;
    }

    @Override
    protected LoginViewModel setCoreViewModel() {
        return new LoginViewModel();
    }

    @OnClick(R.id.tv_phone)
    protected void onPhoneClicked() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            callOTPActivity();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, APP_PERMISSION_CODE);
        }
    }

    private void callOTPActivity() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder = new AccountKitConfiguration.
                AccountKitConfigurationBuilder(LoginType.PHONE, AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == APP_REQUEST_CODE) {

            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getAccessToken() != null && loginResult.getAccessToken().getToken() != null) {
                getViewModel().login(loginResult.getAccessToken().getToken());
            } else {
                ConsoleLog.i(TAG, "Access token is null");
                ViewUtil.t(this, "Unable to login");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == APP_PERMISSION_CODE) {
            //Call OTP even if sms read permission not given assuming fcare will enter OTP manually.
            callOTPActivity();
        }
    }

    @Override
    public void showLoader(boolean isShow) {
        if (isShow) {
            phoneLoginButton.setVisibility(View.GONE);
            loginProgress.setVisibility(View.VISIBLE);
        } else {
            phoneLoginButton.setVisibility(View.GONE);
            loginProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void askUserDetails() {
        FragmentNavigator.navigateToFragment(this, UserNameAndPicFragment.newInstance(), false, containerLayout.getId());
    }

    @Override
    public void goToHome() {
        HomeActivity.openHomeActivity(this);
        finish();
    }

    @Override
    public void showLoginFailed() {
        phoneLoginButton.setVisibility(View.VISIBLE);
        loginProgress.setVisibility(View.GONE);
        ViewUtil.t(this, "unable to login");
    }

    @Override
    public void showLoginNeeded() {
        phoneLoginButton.setVisibility(View.VISIBLE);
        loginProgress.setVisibility(View.GONE);
    }

    @Override
    public void goToPermissionPage() {
        PermissionCheckActivity.openPermissionCheckActivity(this);
        finish();
    }


}
