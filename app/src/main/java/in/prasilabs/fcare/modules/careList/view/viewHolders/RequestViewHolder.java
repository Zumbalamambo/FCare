package in.prasilabs.fcare.modules.careList.view.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prasilabs.pojos.responsePojos.UserDataVO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.prasilabs.fcare.R;
import in.prasilabs.fcare.internalPojos.RelationShipWithStatusData;
import in.prasilabs.fcare.modules.careList.view.CareListCallback;
import in.prasilabs.fcare.utils.ViewUtil;

/**
 * Created by Contus team on 6/9/17.
 */

public class RequestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.user_image)
    ImageView userImage;

    @BindView(R.id.user_name)
    TextView userName;

    private UserDataVO userDataVO;

    private CareListCallback careListCallback;

    public RequestViewHolder(View itemView, CareListCallback careListCallback) {
        super(itemView);
        this.careListCallback = careListCallback;

        ButterKnife.bind(this, itemView);
    }

    public void renderData(RelationShipWithStatusData relationShipWithStatusData) {

        userDataVO = relationShipWithStatusData.getUserRelationShipVO().getUserDataVO();

        if(userDataVO != null) {

            ViewUtil.renderImage(userImage, userDataVO.getUserPictureUrl(), true, userDataVO.getName());

            userName.setText(ViewUtil.toCamelCase(userDataVO.getName()));
        }
    }

    @OnClick(R.id.ll_accept)
    void onAcceptClicked() {
        if(careListCallback != null) {
            careListCallback.acceptUser(userDataVO.getId(), true);
        }
    }

    @OnClick(R.id.ll_block)
    void onBlockClicked() {
        if(careListCallback != null) {
            careListCallback.acceptUser(userDataVO.getId(), false);
        }
    }
}
