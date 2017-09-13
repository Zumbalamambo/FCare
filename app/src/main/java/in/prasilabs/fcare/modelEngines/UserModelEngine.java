package in.prasilabs.fcare.modelEngines;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.prasilabs.pojos.responsePojos.UserDataVO;

import in.prasilabs.fcare.base.BaseApp;
import in.prasilabs.fcare.services.api.ApiManager;
import in.prasilabs.fcare.services.api.FCallBack;
import in.prasilabs.fcare.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Contus team on 29/8/17.
 */

public class UserModelEngine extends CoreModelEngine {

    private static final String TAG = UserModelEngine.class.getSimpleName();

    private static UserModelEngine instance;

    public static UserModelEngine getInstance() {

        if (instance == null) {
            instance = new UserModelEngine();
        }

        return instance;
    }

    public void verifyOTP(String accessToken, final OtpCallback otpCallback) {

        final Call<UserDataVO> otpCall = ApiManager.getInstance().getUserRequest().verifyOTP(accessToken);
        otpCall.enqueue(new FCallBack<UserDataVO>() {
            @Override
            public void onResponse(Call<UserDataVO> call, Response<UserDataVO> response) {

                UserDataVO userDataVO = response.body();

                if (otpCallback != null) {
                    if (userDataVO != null) {
                        otpCallback.getUserData(userDataVO, null);
                    } else {
                        ConsoleLog.i(TAG, "call success but no fcare data");
                        otpCallback.getUserData(null, "Unable to login");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDataVO> call, Throwable t) {
                if (otpCallback != null) {
                    ConsoleLog.t(t);
                    otpCallback.getUserData(null, "Unable to login");
                }
            }
        });
    }

    public void setUserData(@NonNull String name, @Nullable String userPic, final SetUserDataCallback setUserDataCallback) {

        final Call<UserDataVO> userDataCall = ApiManager.getInstance().getUserRequest().setUserData(name, Utils.getDeviceId(BaseApp.getAppContext()), userPic);
        userDataCall.enqueue(new FCallBack<UserDataVO>() {
            @Override
            public void onResponse(Call<UserDataVO> call, Response<UserDataVO> response) {

                UserDataVO userDataVO = response.body();

                if (setUserDataCallback != null) {
                    if (userDataVO != null) {
                        setUserDataCallback.getUserData(userDataVO, null);
                    } else {
                        setUserDataCallback.getUserData(null, "Unable to login");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDataVO> call, Throwable t) {
                if (setUserDataCallback != null) {
                    setUserDataCallback.getUserData(null, "Unable to login");
                }
                ConsoleLog.t(t);
            }
        });
    }

    public interface OtpCallback {

        void getUserData(UserDataVO userDataVO, String errorMessage);
    }

    public interface SetUserDataCallback {
        void getUserData(UserDataVO userDataVO, String errorMessage);
    }
}
