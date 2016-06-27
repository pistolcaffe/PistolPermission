package com.pistolcaffe.lib.pistolpermission.adapter;

import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.pistolcaffe.lib.pistolpermission.PistolLogger;
import com.pistolcaffe.lib.pistolpermission.R;
import com.pistolcaffe.lib.pistolpermission.model.PistolPermissionGroup;

public class PistolPermissionGroupViewHolder extends ParentViewHolder {
    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    private ImageView mIconImageView;
    private TextView mPermLabelTextView;
    private ImageView mListArrowImageView;

    public PistolPermissionGroupViewHolder(View itemView) {
        super(itemView);
        mIconImageView = (ImageView) itemView.findViewById(R.id.icon);
        mPermLabelTextView = (TextView) itemView.findViewById(R.id.label);
        mListArrowImageView = (ImageView) itemView.findViewById(R.id.arrow);
    }

    public void bind(PistolPermissionGroup permGroup) {
        mIconImageView.setImageResource(permGroup.loadIcon());
        mPermLabelTextView.setText(permGroup.loadLabel());
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        PistolLogger.LOGE("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (expanded) {
                mListArrowImageView.setRotation(ROTATED_POSITION);
            } else {
                mListArrowImageView.setRotation(INITIAL_POSITION);
            }
        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            mListArrowImageView.startAnimation(rotateAnimation);
        }
    }
}