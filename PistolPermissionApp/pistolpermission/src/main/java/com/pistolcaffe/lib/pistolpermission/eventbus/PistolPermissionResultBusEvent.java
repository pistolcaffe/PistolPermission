package com.pistolcaffe.lib.pistolpermission.eventbus;

import java.util.List;

public class PistolPermissionResultBusEvent {
    private List<String> mPermList;

    public PistolPermissionResultBusEvent(List<String> permList) {
        mPermList = permList;
    }

    public boolean existDeniedPermission() {
        return mPermList.size() > 0;
    }

    public List<String> getDeniedPermissionList() {
        return mPermList;
    }
}
