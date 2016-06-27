package com.pistolcaffe.lib.pistolpermission.model;

import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

public class PistolPermissionGroup extends PistolPermissionItemInfo implements ParentListItem {

    private String mPermission;
    private PackageManager mPackageManager;
    private List<PistolPermission> mPistolPermissionInfoList = new ArrayList<PistolPermission>();

    public PistolPermissionGroup(PackageManager packageManager, String permission) {
        super(packageManager);
        mPackageManager = packageManager;
        mPermission = permission;
    }

    public void addPermission(String permName) {
        mPistolPermissionInfoList.add(new PistolPermission(mPackageManager, permName));
    }

    public int permissionSize() {
        return mPistolPermissionInfoList.size();
    }

    @Override
    public PackageItemInfo getItemInfo() {
        try {
            return mPackageManager.getPermissionGroupInfo(mPermission, PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<?> getChildItemList() {
        return mPistolPermissionInfoList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}