package com.example.imagepro;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class HandTalk extends AppCompatActivity {
    private EditText inputText;
    private LinearLayout animationContainer;
    private String[] words;
    private int currentWordIndex;
    private LinearLayout customKeyboardLayout;
    StringBuilder mergedVideoPath = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_talk);
        inputText = findViewById(R.id.inputText);
        inputText.requestFocus();
        animationContainer = findViewById(R.id.animationContainer);
        Button processButton = findViewById(R.id.processButton);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processInput();
            }
        });
    }

    private void processInput() {
        String userInput = inputText.getText().toString();
        // Split the input into words
            words = userInput.split("\\s");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputText.getWindowToken(), 0);

        // Start playing videos for each word
        currentWordIndex = 0;
        playNextWordVideo();
    }

    private void playNextWordVideo() {
        if (currentWordIndex < words.length) { //1<2
            String word = words[currentWordIndex];
            int videoResourceId = 0;
            if(isNumber(word)==true){
                if(word.length()>1){
                    String[] charactersArray = new String[word.length()];
                    for (int i = 0; i < word.length(); i++) {
                        charactersArray[i] = String.valueOf(word.charAt(i));
                    }

                    for(String s : charactersArray){
                        int videoResourceId1 = getVideoResourceId1(s);
                        playCharVideo(videoResourceId1);
                    }

                }
                else
                    videoResourceId = getVideoResourceId1(word);
            }
            else
                videoResourceId = getVideoResourceId(word);

            if (videoResourceId != 0) {
                // Display sign language video for the word
                VideoView videoView = createVideoView(videoResourceId);
                String videoPath = "android.resource://" + getPackageName() + "/" + videoResourceId;
                mergedVideoPath.append(videoPath).append(" ");

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
                String[] charactersArray = new String[inputString.length()];

                // Convert each character to a string and store in the array
                for (int i = 0; i < inputString.length(); i++) {
                    charactersArray[i] = String.valueOf(inputString.charAt(i));
                }
                for(String s : charactersArray){
                    int videoResourceId1 = getVideoResourceId(s);
                    playCharVideo(videoResourceId1);
                }
                currentWordIndex++;
                playNextWordVideo();
            }
        }
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

    private void playCharVideo(int videoResourceId1) {
        if (videoResourceId1 != 0) {
            // Display sign language video for the word
            VideoView videoView = createVideoView(videoResourceId1);
            String videoPath = "android.resource://" + getPackageName() + "/" + videoResourceId1;
            mergedVideoPath.append(videoPath).append(" ");
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

    private int getVideoResourceId1(String word) {
        // Check if the video resource exists in the res/raw folder
        int videoResourceId = getResources().getIdentifier("nm_"+ word.toLowerCase() + "_sign", "raw", getPackageName());
        return videoResourceId;
    }
    private int getVideoResourceId(String word) {
        // Check if the video resource exists in the res/raw folder
        int videoResourceId = getResources().getIdentifier(word.toLowerCase() + "_sign", "raw", getPackageName());
        return videoResourceId;
    }
    public void onSignSymbolClick(View view) {
        // Extract the symbol from the clicked button
        String symbol = ((Button) view).getText().toString();

        // Append the symbol to the EditText
        inputText.append(symbol);
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

}
