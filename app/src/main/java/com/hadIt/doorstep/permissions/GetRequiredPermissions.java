package com.hadIt.doorstep.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hadIt.doorstep.utils.Constants;

public class GetRequiredPermissions {
    private Context context;
    private Activity activity;
    private String[] cameraPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public GetRequiredPermissions(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public boolean checkCameraPermissions(){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==(PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkStoragePermissions(){
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==(PackageManager.PERMISSION_GRANTED);
    }

    public void requestCameraPermission(){
        ActivityCompat.requestPermissions(activity, cameraPermissions, Constants.CAMERA_REQUEST_CODE);
    }

    public void requestStoragePermission(){
        ActivityCompat.requestPermissions(activity, storagePermissions, Constants.STORAGE_REQUEST_CODE);
    }
}
