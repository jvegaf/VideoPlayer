package com.github.jvegaf.videoplayer;

import static com.github.jvegaf.videoplayer.AllowAccessActivity.REQUEST_PERMISSION_SETTING;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MediaFiles> mediaFiles = new ArrayList<>();
    private ArrayList<String> allFolderList = new ArrayList<>();
    RecyclerView recyclerView;
    VideoFoldersAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Click on permissions and allow storage", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            //noinspection deprecation
            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        }
        recyclerView = findViewById(R.id.folders_rv);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_folders);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            showFolders();
            swipeRefreshLayout.setRefreshing(false);
        });
        showFolders();
    }

    private void showFolders() {
        mediaFiles = fetchMedia();
        adapter = new VideoFoldersAdapter(mediaFiles, allFolderList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter.notifyDataSetChanged();
    }

    private ArrayList<MediaFiles> fetchMedia() {
        ArrayList<MediaFiles> mediaFilesArrayList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToNext()) {
                do {
                    @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                    @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                    @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                    @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                    @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                    @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    @SuppressLint("Range") String dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));

                    MediaFiles file = new MediaFiles(id, title, displayName, size, duration, path, dateAdded);

                    int index = path.lastIndexOf("/");
                    String subString = path.substring(0, index);
                    if (!allFolderList.contains(subString)) {
                        allFolderList.add(subString);
                    }

                    mediaFilesArrayList.add(file);
                } while (cursor.moveToNext());
            }
        }
        return mediaFilesArrayList;
    }

}