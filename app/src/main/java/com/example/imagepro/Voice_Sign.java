package com.example.imagepro;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Voice_Sign extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;
    private TextView tvText;

    private String[] words;
    private int currentWordIndex;

    private LinearLayout animationContainer;

    String val ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_sign);
        tvText = findViewById(R.id.tvText);
        ImageButton btnSpeak = findViewById(R.id.btnSpeak);
        animationContainer = findViewById(R.id.videoContainer); // Initialize animationContainer
        btnSpeak.setOnClickListener(view -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            try {
                startActivityForResult(intent, RESULT_SPEECH);
                tvText.setText("");
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RESULT_SPEECH) {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvText.setText(text.get(0));
                    val = text.get(0);
                    processInput();

                }
            }
            System.out.println(val);
        }

        private void processInput() {

            String userInput = val;
            words = userInput.split("\\s");

            currentWordIndex = 0;
            playNextWordVideo();

        }

        private void playNextWordVideo() {
            if (currentWordIndex < words.length) {
                String word = words[currentWordIndex];
                int videoResourceId = 0;
                if (isNumber(word)) { // Correct usage of isNumber()
                    if (word.length() > 1) {
                        String[] charactersArray = word.split("");
                        for(String s : charactersArray){
                            int videoResourceId1 = getVideoResourceId1(s);
                            playCharVideo(videoResourceId1);
                        }

                    } else {
                        videoResourceId = getVideoResourceId1(word);
                    }
                } else {
                    videoResourceId = getVideoResourceId(word);
                }

                if (videoResourceId != 0) {
                    // Display sign language video for the word
                    VideoView videoView = createVideoView(videoResourceId);
                    String videoPath = "android.resource://" + getPackageName() + "/" + videoResourceId;
                    animationContainer.addView(videoView);

                    // Set completion listener to play the next word video
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // Remove the completed video
                            animationContainer.removeView(videoView);
                            // Play the next word video
                            currentWordIndex++;
                            playNextWordVideo();
                        }
                    });
                } else {
                    // Handle the case when the video for the word is not found
                    // You can customize this part based on your requirements
                    // In this example, move on to the next word
                    String inputString = word;
                    String[] charactersArray = inputString.split("");
                    for(String s : charactersArray){
                        int videoResourceId1 = getVideoResourceId(s);
                        playCharVideo(videoResourceId1);
                    }
                    currentWordIndex++;
                    playNextWordVideo();
                }
            }
        }

        private void playCharVideo(int videoResourceId1) {
            if (videoResourceId1 != 0) {
                // Display sign language video for the word
                VideoView videoView = createVideoView(videoResourceId1);
                String videoPath = "android.resource://" + getPackageName() + "/" + videoResourceId1;
                animationContainer.addView(videoView);

                // Set completion listener to play the next word video
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // Remove the completed video
                        animationContainer.removeView(videoView);
                    }
                });
            }

        }
        private VideoView createVideoView(int videoResourceId) {
            // Create a VideoView for the given video resource ID
            VideoView videoView = new VideoView(this);
            videoView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + videoResourceId);
            videoView.start();
            return videoView;
        }

        private int getVideoResourceId(String word) {
            // Check if the video resource exists in the res/raw folder
            int videoResourceId = getResources().getIdentifier(word.toLowerCase() + "_sign", "raw", getPackageName());
            return videoResourceId;
        }
        private int getVideoResourceId1(String word) {
            // Check if the video resource exists in the res/raw folder
            int videoResourceId = getResources().getIdentifier("nm_"+ word.toLowerCase() + "_sign", "raw", getPackageName());
            return videoResourceId;
        }

        private boolean isNumber(String word) {

            try {
                // Try parsing the string as an integer
                Long.parseLong(word);
                return true;
            } catch (NumberFormatException e1) {
                return false;
            }
        }
    }
