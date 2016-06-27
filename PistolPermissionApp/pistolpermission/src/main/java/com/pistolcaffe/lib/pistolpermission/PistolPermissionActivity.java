package com.pistolcaffe.lib.pistolpermission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.pistolcaffe.lib.pistolpermission.eventbus.PistolBusProvider;
import com.pistolcaffe.lib.pistolpermission.eventbus.PistolPermissionResultBusEvent;

import java.util.ArrayList;
import java.util.List;

public class PistolPermissionActivity extends AppCompatActivity {

    private final int REQUESTCODE_PISTOL_PERMISSION = 0;

    private List<String> mDeniedPermissionList = new ArrayList<String>();
    private String mTargetPackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pistol_permission);

        if (getIntent() != null) {
            mDeniedPermissionList = getIntent().getStringArrayListExtra(PistolValues.EXTRA_PERMISSION);
            mTargetPackageName = getIntent().getStringExtra(PistolValues.EXTRA_PACKAGE_NAME);
        }

        List<String> rationalPermissionList = new ArrayList<String>();
        for (String deniedPermission : mDeniedPermissionList) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, deniedPermission)) {
                rationalPermissionList.add(deniedPermission);
            } else {
            }
        }

        if (rationalPermissionList.size() > 0) {
            final PistolPermissionRationalView view = (PistolPermissionRationalView) getLayoutInflater().inflate(R.layout.layout_rational_permission_view, null);
            view.setData(rationalPermissionList, mTargetPackageName);
            view.buildView();

            new AlertDialog.Builder(this, R.style.DialogTheme).setView(view).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermission();
                }
            }).setCancelable(false).show();
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, mDeniedPermissionList.toArray(new String[mDeniedPermissionList.size()]), REQUESTCODE_PISTOL_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> deniedPermissionList = new ArrayList<String>();

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissionList.add(permissions[i]);
            }
        }

        PistolBusProvider.getBus().post(new PistolPermissionResultBusEvent(deniedPermissionList));
        finish();
    }
}