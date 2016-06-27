package com.pistolcaffe.lib.pistolpermission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pistolcaffe.lib.pistolpermission.adapter.PistolPermissionAdapter;
import com.pistolcaffe.lib.pistolpermission.model.PistolPermissionGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PistolPermissionRationalView extends RelativeLayout {

    private List<String> mRationalPermissionList;
    private String mTargetPackageName;

    private ImageView mAppIconImageView;
    private TextView mAppNameTextView;
    private RecyclerView mPermissionListRecyclerView;

    private LinearLayoutManager mLayoutManager;

    private PackageManager mPackageManager;

    public PistolPermissionRationalView(Context context) {
        super(context);
    }

    public PistolPermissionRationalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPackageManager = getContext().getPackageManager();
    }

    public void setData(List<String> rationalPermissionList, String packageName) {
        mRationalPermissionList = rationalPermissionList;
        mTargetPackageName = packageName;
    }

    public void buildView() {
        mAppNameTextView.setText(getAppNameFromPackageName());

        mAppIconImageView.setImageDrawable(getAppIconFromPackageName());

        HashMap<String, PistolPermissionGroup> groupsMap = new HashMap<String, PistolPermissionGroup>();
        for (String permission : mRationalPermissionList) {
            try {
                PermissionInfo info = mPackageManager.getPermissionInfo(permission, PackageManager.GET_PERMISSIONS);
                String permName = info.name;
                String groupName = info.group != null ? info.group : permName;

                PistolPermissionGroup group = groupsMap.get(groupName);
                if (group == null) {
                    group = new PistolPermissionGroup(mPackageManager, groupName);
                    groupsMap.put(groupName, group);
                }
                group.addPermission(info.name);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        PistolPermissionAdapter adapter = new PistolPermissionAdapter(getContext(), new ArrayList<PistolPermissionGroup>(groupsMap.values()));
        mPermissionListRecyclerView.setHasFixedSize(false);
        mPermissionListRecyclerView.setAdapter(adapter);
    }


    private String getAppNameFromPackageName() {
        try {
            return mPackageManager.getApplicationLabel(mPackageManager.getApplicationInfo(mTargetPackageName, PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    private Drawable getAppIconFromPackageName() {
        try {
            return mPackageManager.getApplicationIcon(mPackageManager.getApplicationInfo(mTargetPackageName, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        PistolLogger.LOGE("");
        mAppIconImageView = (ImageView) findViewById(R.id.app_icon);
        mAppNameTextView = (TextView) findViewById(R.id.app_name);

        mLayoutManager = new LinearLayoutManager(getContext());
        mPermissionListRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mPermissionListRecyclerView.setLayoutManager(mLayoutManager);
    }
}