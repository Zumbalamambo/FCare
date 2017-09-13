package in.prasilabs.fcare.services.api;

import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import java.io.IOException;

import in.prasilabs.fcare.base.BaseApp;
import in.prasilabs.fcare.managers.FUserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prasi on 5/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public abstract class FCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.code() == 401) {
            FUserManager.getInstance(BaseApp.getAppContext()).logout();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        ConsoleLog.t(t);
    }
}
