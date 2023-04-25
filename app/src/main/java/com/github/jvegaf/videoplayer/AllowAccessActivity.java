package com.github.jvegaf.videoplayer;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AllowAccessActivity extends AppCompatActivity {

    public static final int STORAGE_PERMISSION = 1;
    public static final int REQUEST_PERMISSION_SETTING = 12;
    Button allowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_access);

        allowBtn = findViewById(R.id.allow_access_btn);

        SharedPreferences preferences = getSharedPreferences("AllowAccess", MODE_PRIVATE);

        String value = preferences.getString("Allow", "");

        if (value.equals("OK")) {
            startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
            finish();
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Allow","OK");
            editor.apply();
        }


        allowBtn.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
                finish();
            } else {
                ActivityCompat.requestPermissions(AllowAccessActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                String per = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = shouldShowRequestPermissionRationale(per);
                    if (!showRationale) {
                        // user clicked on never ask again
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("App Permission").setMessage(
                                "\n\nFor play videos, you must allow this app to access video files on your device\n\n" +
                                        "Now follow the below steps\n\n" +
                                        "Open Settings from below button\n" +
                                        "Click on Permissions\n" +
                                        "Allow access to storage")
                                .setPositiveButton("Open Settings", (dialog, which) -> {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    //noinspection deprecation
                                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                }).create().show();
                    } else {
                        ActivityCompat.requestPermissions(AllowAccessActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
                    }
                } else {
                    startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
                    finish();
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(AllowAccessActivity.this, MainActivity.class));
            finish();
        }
    }
}