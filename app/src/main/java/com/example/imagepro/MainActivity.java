package com.example.imagepro;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.opencv.android.OpenCVLoader;


public class MainActivity extends AppCompatActivity {
    private LinearLayout move;
    private LinearLayout vidCardView;

    private CardView voice;
    private LinearLayout sig,hand;

    private CardView cr;
   // private LinearLayout  key = findViewById(R.id.key);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(OpenCVLoader.initDebug()) Log.d("LOADED","SUCCESS");
        else Log.d("LOADED","ERROR");
        vidCardView =  findViewById(R.id.vidLayout);
        sig = findViewById(R.id.sign);
        hand = findViewById(R.id.HandDetect);
        move = findViewById(R.id.Move);
        cr = findViewById(R.id.key);
        voice = findViewById(R.id.Voice);
        hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
       sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Sign_Guide.class);
                startActivity(intent);
            }
        });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Voice_Sign.class);
                startActivity(intent);
            }
        });

        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Keyboard.class);
                startActivity(intent);
            }
        });
       vidCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Contact.class);
                startActivity(intent);
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MainActivity.this, HandTalk.class);
                                        startActivity(intent);
                                    }
                                }

        );
    }


}
