package com.pistolcaffe.lib.pistolpermission.model;

import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;

public abstract class PistolPermissionItemInfo {

    private PackageManager mPackageManager;

    public PistolPermissionItemInfo(PackageManager packageManager) {
        mPackageManager = packageManager;
    }

    public int loadIcon() {
        if (getItemInfo() instanceof PermissionGroupInfo) {
            return getItemInfo().icon;
        } else {
            return getGroupInfoWithChildPermission().icon;
        }
    }

    public String loadLabel() {
        return getItemInfo().loadLabel(mPackageManager).toString();
    }

    public String loadDescription() {
        if (getItemInfo() instanceof PermissionGroupInfo) {
            return ((PermissionGroupInfo) getItemInfo()).loadDescription(mPackageManager).toString();
        } else {
            return ((PermissionInfo) getItemInfo()).loadDescription(mPackageManager).toString();
        }
    }

    private PermissionGroupInfo getGroupInfoWithChildPermission() {
        try {
            return mPackageManager.getPermissionGroupInfo(((PermissionInfo) getItemInfo()).group, PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract PackageItemInfo getItemInfo();
}