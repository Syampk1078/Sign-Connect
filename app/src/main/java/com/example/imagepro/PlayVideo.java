package com.example.imagepro;

import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        // Retrieve the reference to the LinearLayout
        LinearLayout animationContainer = findViewById(R.id.animationContainer);

        // Create a VideoView instance
        VideoView videoView = new VideoView(this);

        // Set the layout parameters for the VideoView
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        videoView.setLayoutParams(layoutParams);

        // Set the video path to the video file in the raw folder
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.asl);
        videoView.setVideoURI(videoUri);

        // Add the VideoView to the LinearLayout
        animationContainer.addView(videoView);

        // Start playing the video
        videoView.start();
    }
}
