package in.prasilabs.fcare.managers;

import android.content.Context;

import com.prasilabs.pojos.responsePojos.UserDataVO;
import com.prasilabs.droidwizardlib.utils.JsonUtil;
import com.prasilabs.droidwizardlib.utils.LocalPreference;

import org.json.JSONObject;

/**
 * Created by Contus team on 30/8/17.
 */

public class FUserManager {

    private static FUserManager instance;

    private static final String USER_DATA_STR = "user_data";

    private UserDataVO userDataVO;

    private Context context;

    private FUserManager(){}

    public static FUserManager getInstance(Context context) {

        if(instance == null) {
            instance = new FUserManager();
        }
        instance.setUserData(context);

        return instance;
    }

    private void setUserData(Context context) {

        this.context = context;

        String userDataStr = LocalPreference.getUserDataFromShared(context, USER_DATA_STR, null);

        userDataVO = JsonUtil.getObjectFromJson(userDataStr, UserDataVO.class);
    }

    public UserDataVO getUserDataVO() {
        return userDataVO;
    }

    public void setUserDataVO(UserDataVO userDataVO) {
        this.userDataVO = userDataVO;

        JSONObject userDataObject = JsonUtil.getJsonFromClass(userDataVO);

        LocalPreference.saveUserDataInShared(context, USER_DATA_STR, userDataObject.toString());
    }

    public void logout() {
        LocalPreference.clearUserSharedPreferences(context);
    }
}
