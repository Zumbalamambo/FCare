package in.prasilabs.fcare.modules.login.userDetails;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;

import butterknife.BindView;
import butterknife.OnClick;
import in.prasilabs.fcare.R;
import in.prasilabs.fcare.base.BaseFragment;
import in.prasilabs.fcare.modules.login.splash.LoginActivity;
import in.prasilabs.fcare.utils.PermissionUtils;
import in.prasilabs.fcare.utils.ViewUtil;

/**
 * Created by Contus team on 30/8/17.
 */

public class UserNameAndPicFragment extends BaseFragment<UserNameAndPicViewModel> implements UserNameAndPicViewModelCallback {

    @BindView(R.id.iv_profile)
    ImageView profileImage;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.ll_btn)
    LinearLayout btnLayout;

    @BindView(R.id.et_profile)
    TextView nameText;

    private String profilePictureUrl;

    public static UserNameAndPicFragment newInstance() {
        return new UserNameAndPicFragment();
    }

    @Override
    protected UserNameAndPicViewModel setViewModel() {
        return new UserNameAndPicViewModel();
    }

    @Override
    protected void initializeView(Bundle bundle) {
        btnLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_user_name_pic;
    }

    @Override
    protected CoreCallBack getCoreCallBack() {
        return this;
    }

    @OnClick(R.id.fl_profile)
    void onProifleImageClicked() {
        //TODO ask for image pic.
    }

    @OnClick(R.id.bt_continue)
    void onContinueClicked() {
        getViewModel().setProfileData(nameText.getText().toString(), profilePictureUrl);
    }

    @Override
    public void goToHomeActivity() {
        if(getActivity() instanceof LoginActivity) {
            if(PermissionUtils.checkMandatoryPermission(getContext())) {
                ((LoginActivity) getActivity()).goToHome();
            } else {
                ((LoginActivity) getActivity()).goToPermissionPage();
            }
        }
    }

    @Override
    public void showError(String erroMessage) {
        ViewUtil.t(getContext(), erroMessage);
    }

    @Override
    public void showLoading(boolean isShow) {
        if(isShow) {
            progressBar.setVisibility(View.VISIBLE);
            btnLayout.setVisibility(View.GONE);
        } else {
            btnLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
