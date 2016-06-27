package com.pistolcaffe.lib.pistolpermission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.pistolcaffe.lib.pistolpermission.eventbus.PistolBusProvider;
import com.pistolcaffe.lib.pistolpermission.eventbus.PistolPermissionResultBusEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class PistolPermissionChecker {

    private Params mParams;

    public interface OnPistolPermissionResultCallback {
        public void onGranted();

        public void onDenied(List<String> deniedPermList);
    }

    private PistolPermissionChecker(Params p) {
        mParams = p;
        PistolBusProvider.getBus().register(this);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mParams.callback.onGranted();
            return;
        }

        ArrayList<String> deniedPermissionList = new ArrayList<String>();

        for (String permission : mParams.permissions) {
            //TODO :: modify rational check routine?
            if (ActivityCompat.checkSelfPermission(mParams.activity, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissionList.add(permission);
            }
        }

        //모든 퍼미션이 수락 되었을때
        if (deniedPermissionList.size() == 0) {
            if (mParams.callback != null) {
                mParams.callback.onGranted();
            }
            return;
        }

        Intent intent = new Intent(mParams.activity, PistolPermissionActivity.class);
        intent.putStringArrayListExtra(PistolValues.EXTRA_PERMISSION, deniedPermissionList);
        intent.putExtra(PistolValues.EXTRA_PACKAGE_NAME, mParams.activity.getPackageName());

        mParams.activity.startActivity(intent);
    }

    @Subscribe
    public void onCheckFinished(PistolPermissionResultBusEvent result) {
        PistolLogger.LOGE("");
        PistolBusProvider.getBus().unregister(this);

        if (mParams.snackBarTargetView == null) {
            mParams.activity.getWindow().getDecorView().findViewById(android.R.id.content);
        }

        if (mParams.callback != null) {
            if (result.existDeniedPermission()) {
                mParams.callback.onDenied(result.getDeniedPermissionList());
            } else {
                mParams.callback.onGranted();
            }
        }

        if (result.existDeniedPermission()) {
            Snackbar.make(mParams.snackBarTargetView, R.string.str_permissions_denied, Snackbar.LENGTH_SHORT).setAction(R.string.str_setting, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(Uri.parse("package:" + mParams.activity.getPackageName()));
                    mParams.activity.startActivity(intent);
                }
            }).show();
        }
    }

    private static class Params {
        private Activity activity;
        private String[] permissions;
        private View snackBarTargetView;
        private OnPistolPermissionResultCallback callback;
    }

    public static class Builder {
        private Params params;

        public Builder(Activity activity) {
            params = new Params();
            params.activity = activity;
        }

        public Builder setPermissions(String[] permissions) {
            params.permissions = permissions;
            return this;
        }

        public Builder setPermissionDeniedSnackBarTargetView(View snackBarTargetView) {
            params.snackBarTargetView = snackBarTargetView;
            return this;
        }

        public Builder setOnPermissionResultCallback(OnPistolPermissionResultCallback callback) {
            params.callback = callback;
            return this;
        }

        public void start() {
            new PistolPermissionChecker(params).checkPermission();
        }
    }
}