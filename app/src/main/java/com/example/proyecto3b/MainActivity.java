package com.example.proyecto3b;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    private Button playButton;
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.videoView);
        playButton = findViewById(R.id.playButton);
        String videoPath = "android.resource://" + getPackageName() + "/" +
                R.raw.mivideo;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        /*
        * String videoUrl = "https://www.example.com/video.mp4";
        * Uri uri = Uri.parse(videoUrl);
        * videoView.setVideoURI(uri);
        */
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    playButton.setText("Reproducir");
                } else {
                    videoView.start();
                    playButton.setText("Pausar");
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}