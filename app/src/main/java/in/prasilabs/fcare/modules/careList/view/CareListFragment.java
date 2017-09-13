package in.prasilabs.fcare.modules.careList.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;

import java.util.List;

import butterknife.BindView;
import in.prasilabs.fcare.R;
import in.prasilabs.fcare.base.BaseFragment;
import in.prasilabs.fcare.internalPojos.FirebaseStatusData;
import in.prasilabs.fcare.internalPojos.RelationShipWithStatusData;
import in.prasilabs.fcare.modules.careList.viewModel.CareListViewModel;
import in.prasilabs.fcare.modules.careList.viewModel.CareListViewModelCallback;
import in.prasilabs.fcare.utils.ViewUtil;

/**
 * Created by Contus team on 31/8/17.
 */

public class CareListFragment extends BaseFragment<CareListViewModel> implements CareListViewModelCallback, CareListCallback {

    private static final int REQ_FOR_PICK_CONTACT = 23;
    private static final int REQ_FOR_READ_CONTACT = 21;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.root_layout)
    FrameLayout rootLayout;
    private CareListAdapter careListAdapter;

    public static CareListFragment newInstance() {
        return new CareListFragment();
    }

    @Override
    protected CareListViewModel setViewModel() {
        return new CareListViewModel();
    }

    @Override
    protected void initializeView(Bundle bundle) {

        careListAdapter = new CareListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(careListAdapter);

        getViewModel().getRelations();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_care_list;
    }

    @Override
    protected CoreCallBack getCoreCallBack() {
        return this;
    }

    @Override
    public void userAddClicked() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            askPickContact();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQ_FOR_READ_CONTACT);
        }
    }

    private void askPickContact() {
        Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(pickContact, REQ_FOR_PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_FOR_PICK_CONTACT && resultCode == Activity.RESULT_OK) {

            String name = null;
            String number = null;

            Uri contactUri = data.getData();
            Cursor nameCursor = getContext().getContentResolver().query(contactUri, null, null, null, null);
            if (nameCursor != null) {
                if (nameCursor.moveToFirst()) {
                    name = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    number = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                nameCursor.close();
            }

            if (name != null && number != null) {
                addRelation(name, number);
            }
        }
    }

    private void addRelation(String name, String no) {
        getViewModel().addRelation(name, no);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_FOR_READ_CONTACT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                askPickContact();
            }
        }
    }

    @Override
    public void userStatusClicked() {

    }

    @Override
    public void acceptUser(long userId, boolean isAccept) {
        getViewModel().acceptUser(userId, isAccept);
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            ViewUtil.showProgressView(rootLayout, true);
        } else {
            ViewUtil.hideProgressView(rootLayout);
        }
    }

    @Override
    public void showRelations(final List<RelationShipWithStatusData> userRelationShipVOList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                careListAdapter.clearAndAddItem(userRelationShipVOList);
            }
        });
    }

    @Override
    public void updateRelation(final RelationShipWithStatusData userRelationShipVO) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                careListAdapter.addItem(userRelationShipVO);
            }
        });
    }

    @Override
    public void updateMyStatus(final FirebaseStatusData firebaseStatusData) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (careListAdapter != null) {
                    careListAdapter.setMyData(firebaseStatusData);
                }
            }
        });
    }
}
