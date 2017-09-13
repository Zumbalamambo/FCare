package in.prasilabs.fcare.modules.careList.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prasilabs.enums.UserRelationShipEnum;
import com.prasilabs.pojos.responsePojos.UserRelationShipVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import in.prasilabs.fcare.R;
import in.prasilabs.fcare.internalPojos.FirebaseStatusData;
import in.prasilabs.fcare.internalPojos.RelationShipWithStatusData;
import in.prasilabs.fcare.modules.careList.view.viewHolders.AddRelationViewHolder;
import in.prasilabs.fcare.modules.careList.view.viewHolders.CareListViewHolder;
import in.prasilabs.fcare.modules.careList.view.viewHolders.MyStatusViewHolder;
import in.prasilabs.fcare.modules.careList.view.viewHolders.PendingViewHolder;
import in.prasilabs.fcare.modules.careList.view.viewHolders.RequestViewHolder;

/**
 * Created by prasi on 1/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class CareListAdapter extends RecyclerView.Adapter {

    private List<RelationShipWithStatusData> relationShipStatusList = new ArrayList<>();

    private static final int VIEW_TYPE_MINE = 1;
    private static final int VIEW_TYPE_RELATION = 2;
    private static final int VIEW_TYPE_REQ = 4;
    private static final int VIEW_TYPE_PENDING = 5;
    private static final int VIEW_TYPE_ADD = 3;

    private CareListCallback careListCallback;

    private FirebaseStatusData myData;

    public CareListAdapter(CareListCallback careListCallback) {
        this.careListCallback = careListCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_MINE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status_mine, parent, false);
            return new MyStatusViewHolder(view, careListCallback);
        } else if(viewType == VIEW_TYPE_RELATION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_status, parent, false);
            return new CareListViewHolder(view, careListCallback);
        } else if(viewType == VIEW_TYPE_ADD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_relation, parent, false);
            return new AddRelationViewHolder(view, careListCallback);
        } else if(viewType == VIEW_TYPE_PENDING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_pending, parent, false);
            return new PendingViewHolder(view, careListCallback);
        } else if(viewType == VIEW_TYPE_REQ) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_req, parent, false);
            return new RequestViewHolder(view, careListCallback);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyStatusViewHolder) {
            ((MyStatusViewHolder) holder).renderData(myData);
        } else if(holder instanceof CareListViewHolder) {
            RelationShipWithStatusData relationShipWithStatusData = relationShipStatusList.get(position-1);
            ((CareListViewHolder) holder).renderData(relationShipWithStatusData);
        } else if(holder instanceof PendingViewHolder) {
            RelationShipWithStatusData relationShipWithStatusData = relationShipStatusList.get(position-1);
            ((PendingViewHolder) holder).renderData(relationShipWithStatusData);
        } else if(holder instanceof RequestViewHolder) {
            RelationShipWithStatusData relationShipWithStatusData = relationShipStatusList.get(position-1);
            ((RequestViewHolder) holder).renderData(relationShipWithStatusData);
        } else if(holder instanceof AddRelationViewHolder) {
            ((AddRelationViewHolder) holder).renderData(relationShipStatusList.isEmpty());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return VIEW_TYPE_MINE;
        } else if(position <= relationShipStatusList.size()) {
            UserRelationShipVO relationShipVO = relationShipStatusList.get(position-1).getUserRelationShipVO();

            if(relationShipVO.getRelationShipEnum().equals(UserRelationShipEnum.REQUESTED.getName())) {
                if(relationShipVO.isIamActor()) {
                    return VIEW_TYPE_REQ;
                } else {
                    return VIEW_TYPE_PENDING;
                }
            } else {
                return VIEW_TYPE_RELATION;
            }

        } else {
            return VIEW_TYPE_ADD;
        }
    }

    @Override
    public int getItemCount() {
        return relationShipStatusList.size() + 2; //1 for my profile and other for add relation.
    }

    public void clearAndAddItem(List<RelationShipWithStatusData> userRelationShipVOList) {
        this.relationShipStatusList.clear();
        if(userRelationShipVOList != null) {
            this.relationShipStatusList.addAll(userRelationShipVOList);
        }
        notifyDataSetChanged();
    }

    public void addItem(RelationShipWithStatusData userRelationShipVO) {
        Iterator<RelationShipWithStatusData> userRelationShipVOIterator = relationShipStatusList.iterator();

        while (userRelationShipVOIterator.hasNext()) {
            RelationShipWithStatusData oldItem = userRelationShipVOIterator.next();

            if(Objects.equals(oldItem.getUserId(), userRelationShipVO.getUserId())) {
                userRelationShipVOIterator.remove();
                break;
            }
        }

        relationShipStatusList.add(userRelationShipVO);
        notifyDataSetChanged();
    }

    public void setMyData(FirebaseStatusData myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }
}
