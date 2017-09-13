package in.prasilabs.fcare.modules.careList.view.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import in.prasilabs.fcare.R;
import in.prasilabs.fcare.modules.careList.view.CareListCallback;

public class AddRelationViewHolder extends RecyclerView.ViewHolder {

    private CareListCallback careListCallback;

    public AddRelationViewHolder(View itemView, CareListCallback careListCallback) {
        super(itemView);
        this.careListCallback = careListCallback;

        ButterKnife.bind(this, itemView);
    }

    public void renderData(boolean isEmptyRelations) {

    }

    @OnClick(R.id.ll_add)
    void onLayoutClicked() {
        if(careListCallback != null) {
            careListCallback.userAddClicked();
        }
    }
}