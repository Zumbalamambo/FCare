package in.prasilabs.fcare.modules.careList.view;

/**
 * Created by Contus team on 6/9/17.
 */

public interface CareListCallback {
    void userAddClicked();

    void userStatusClicked();

    void acceptUser(long userId, boolean isAccept);
}
