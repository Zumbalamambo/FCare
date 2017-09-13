package in.prasilabs.fcare.modules.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;
import com.prasilabs.droidwizardlib.utils.FragmentNavigator;

import butterknife.BindView;
import in.prasilabs.fcare.R;
import in.prasilabs.fcare.base.BaseActivity;
import in.prasilabs.fcare.managers.FInitManager;
import in.prasilabs.fcare.modules.careList.view.CareListFragment;
import in.prasilabs.fcare.utils.LocationUtils;

public class HomeActivity extends BaseActivity<HomeViewModel> implements HomeViewModelCallback {

    @BindView(R.id.container)
    FrameLayout container;

    public static void openHomeActivity(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentNavigator.navigateToFragment(this, CareListFragment.newInstance(), false, container.getId());

        FInitManager.init(this);
    }


    @Override
    protected CoreCallBack getCoreCallBack() {
        return this;
    }

    @Override
    protected HomeViewModel setCoreViewModel() {
        return new HomeViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
