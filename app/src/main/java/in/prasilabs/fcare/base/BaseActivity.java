package in.prasilabs.fcare.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;
import com.prasilabs.droidwizardlib.core.views.CoreActivityView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Contus team on 29/8/17.
 */
public abstract class BaseActivity<T extends CoreViewModel> extends CoreActivityView<T> {

    private Unbinder unbinder;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(unbinder != null) {
            unbinder.unbind();
        }
    }
}
