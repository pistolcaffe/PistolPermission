package com.pistolcaffe.lib.pistolpermission.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.pistolcaffe.lib.pistolpermission.model.PistolPermission;
import com.pistolcaffe.lib.pistolpermission.R;
import com.pistolcaffe.lib.pistolpermission.model.PistolPermissionGroup;

import java.util.List;

public class PistolPermissionAdapter extends ExpandableRecyclerAdapter<PistolPermissionGroupViewHolder, PistolPermissionViewHolder> {
    private LayoutInflater mInflator;
    public Context mContext;

    public PistolPermissionAdapter(Context context, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public PistolPermissionGroupViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View permGroupView = mInflator.inflate(R.layout.permission_group_row, parentViewGroup, false);
        return new PistolPermissionGroupViewHolder(permGroupView);
    }

    @Override
    public PistolPermissionViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View permView = mInflator.inflate(R.layout.permission_row, childViewGroup, false);
        return new PistolPermissionViewHolder(permView, mContext);
    }

    @Override
    public void onBindParentViewHolder(PistolPermissionGroupViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        PistolPermissionGroup permGroup = (PistolPermissionGroup) parentListItem;
        parentViewHolder.bind(permGroup);
    }

    @Override
    public void onBindChildViewHolder(PistolPermissionViewHolder childViewHolder, int position, Object childListItem) {
        PistolPermission perm = (PistolPermission) childListItem;
        childViewHolder.bind(perm);
    }
}