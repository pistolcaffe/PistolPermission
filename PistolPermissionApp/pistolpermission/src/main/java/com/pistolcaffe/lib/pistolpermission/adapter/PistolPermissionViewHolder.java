package com.pistolcaffe.lib.pistolpermission.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.pistolcaffe.lib.pistolpermission.model.PistolPermission;
import com.pistolcaffe.lib.pistolpermission.R;

public class PistolPermissionViewHolder extends ChildViewHolder {

    private RelativeLayout mContent;
    private ImageView mIconImageView;
    private TextView mPermLabelTextView;

    private Context mContext;

    public PistolPermissionViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mContent = (RelativeLayout) itemView.findViewById(R.id.child_row);
        mIconImageView = (ImageView) itemView.findViewById(R.id.icon);
        mPermLabelTextView = (TextView) itemView.findViewById(R.id.label);
    }

    public void bind(final PistolPermission perm) {
        mIconImageView.setImageResource(perm.loadIcon());
        mPermLabelTextView.setText(perm.loadLabel());

        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext).setMessage(perm.loadDescription()).setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }
}