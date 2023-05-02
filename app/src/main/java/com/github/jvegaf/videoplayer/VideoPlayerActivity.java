package com.github.jvegaf.videoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.DefaultHlsDataSourceFactory;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.util.ArrayList;

public class VideoPlayerActivity extends AppCompatActivity {

    ArrayList<MediaFiles> mVideoFiles = new ArrayList<>();
    StyledPlayerView playerView;
    ExoPlayer player;
    int position;
    String videoTitle;
    TextView title;
    ConcatenatingMediaSource concatenatingMediaSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        playerView = findViewById(R.id.exoplayer_view);
        position = getIntent().getIntExtra("position", 1);
        videoTitle = getIntent().getStringExtra("video_title");
        mVideoFiles = getIntent().getExtras().getParcelableArrayList("videoArrayList");
        DefaultHlsDataSourceFactory dataSourceFactory = new DefaultHlsDataSourceFactory(this, Util.getUserAgent(this, "app"));
        concatenatingMediaSource = new ConcatenatingMediaSource();
        for (MediaFiles mediaFiles : mVideoFiles) {
            new File(String.valueOf(mediaFiles.getPath()));
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(uri)));
            concatenatingMediaSource.addMediaItem(new DefaultDataSource.Factory(this).createDataSource());
        }
        playerView.setPlayer(player);

        title = findViewById(R.id.video_title);
        title.setText(videoTitle);

        playVideo();

    }

    private void playVideo() {
       String path = mVideoFiles.get(position).getPath();
        Uri uri = Uri.parse(path);
        player = new ExoPlayer.Builder(this).build();
        DefaultHlsDataSourceFactory dataSourceFactory = new DefaultHlsDataSourceFactory(this, Util.getUserAgent(this, "app"));

    }
}