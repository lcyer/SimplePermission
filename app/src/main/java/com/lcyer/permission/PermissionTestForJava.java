package com.lcyer.permission;

import android.Manifest;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lcyer.simplepermission.PermissionListener;
import com.lcyer.simplepermission.SimplePermission;

import java.util.List;

public class PermissionTestForJava extends ComponentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        Button btnOpenCamera = findViewById(R.id.btn_open_camera);

        btnOpenCamera.setOnClickListener(view -> {
            SimplePermission.builder()
                    .denyTitle("Permission Denied")
                    .denyMessage("please go to Permissions in the app settings and allow it.")
                    .permissions(Manifest.permission.CAMERA)
                    .create()
                    .check(new PermissionListener() {
                        @Override
                        public void onGranted() {
                            new AlertDialog.Builder(PermissionTestForJava.this)
                                    .setMessage("Allow Camera Permission!")
                                    .setPositiveButton("ok", (dialog, which) -> {
                                        dialog.dismiss();
                                    })
                                    .show();
                        }

                        @Override
                        public void onDenied(@NonNull List<String> deniedPermissions) {

                        }
                    });
        });
    }
}
