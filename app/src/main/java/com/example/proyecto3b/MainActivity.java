package com.example.proyecto3b;

import android.health.connect.datatypes.units.Volume;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    private VideoView videoViewLeft;
    private VideoView videoViewRight;
    private VideoView videoView180;
    private Button playButton;
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.videoView);
        videoViewLeft = findViewById(R.id.videoViewLeft);
        videoViewRight = findViewById(R.id.videoViewRight);
        videoView180 = findViewById(R.id.videoView180);

        playButton = findViewById(R.id.playButton);
       String videoPath = "android.resource://" + getPackageName() + "/" +
                R.raw.mivideo1;
       Uri uri = Uri.parse(videoPath);

       videoViewLeft.setRotation(-90);
       videoViewRight.setRotation(90);
       videoView180.setRotation(180);

       videoView.setVideoURI(uri);
       videoViewLeft.setVideoURI(uri);
       videoViewRight.setVideoURI(uri);
       videoView180.setVideoURI(uri);


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
                    videoViewLeft.pause();
                    videoViewRight.pause();
                    videoView180.pause();
                    playButton.setText("Reproducir");
                } else {
                    videoView.start();
                    videoViewLeft.start();
                    videoViewRight.start();
                    videoView180.start();
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
            videoViewLeft.stopPlayback();
            videoViewRight.stopPlayback();
            videoView180.stopPlayback();
        }
    }
}