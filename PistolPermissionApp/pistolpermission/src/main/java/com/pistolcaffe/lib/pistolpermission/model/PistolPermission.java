package com.pistolcaffe.lib.pistolpermission.model;

import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;

public class PistolPermission extends PistolPermissionItemInfo {
    private String mPermission;
    private PackageManager mPackageManager;

    public PistolPermission(PackageManager packageManager, String permission) {
        super(packageManager);
        mPackageManager = packageManager;
        mPermission = permission;
    }

    @Override
    public PackageItemInfo getItemInfo() {
        try {
            return mPackageManager.getPermissionInfo(mPermission, PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
