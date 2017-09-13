package in.prasilabs.fcare.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;
import com.prasilabs.droidwizardlib.core.views.CoreFragmentView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Contus team on 29/8/17.
 */

public abstract class BaseFragment<T extends CoreViewModel> extends CoreFragmentView<T> {

    private Unbinder unbinder;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        if(view != null) {
            unbinder = ButterKnife.bind(this, view);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(unbinder != null) {
            unbinder.unbind();
        }
    }
}
