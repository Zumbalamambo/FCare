package in.prasilabs.fcare.modules.careList.view.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import in.prasilabs.fcare.internalPojos.RelationShipWithStatusData;
import in.prasilabs.fcare.modules.careList.view.CareListCallback;

/**
 * Created by Contus team on 6/9/17.
 */

public class PendingViewHolder extends RecyclerView.ViewHolder {

    private CareListCallback careListCallback;

    public PendingViewHolder(View itemView, CareListCallback careListCallback) {
        super(itemView);
        this.careListCallback = careListCallback;

        ButterKnife.bind(this, itemView);
    }

    public void renderData(RelationShipWithStatusData relationShipWithStatusData) {

    }
}
