package com.xwjr.staple.permission;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class PermissionFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_CODE = 42;
    private PermissionRequest.PermissionListener listener;

    public void requestPermissions(String[] permissions, PermissionRequest.PermissionListener listener) {
        try {
            this.listener = listener;
            if (!PermissionUtils.hasPermission(getContext(), permissions)) {
                requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
            } else {
                listener.permissionGranted();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode != PERMISSIONS_REQUEST_CODE) return;
            if (listener != null) {
                if (PermissionUtils.hasPermission(getContext(), permissions)) {
                    listener.permissionGranted();
                } else {
                    if (PermissionUtils.isNeverAsk(getContext(), permissions)) {
                        listener.permissionNeverAsk(PermissionUtils.getNeverAskPermissions(getContext(), permissions));
                    } else {
                        listener.permissionDenied(PermissionUtils.getDeniedPermissions(getContext(), permissions));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
