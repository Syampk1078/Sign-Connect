package com.example.imagepro;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Keyboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        Button openKeyboardSettingsButton = findViewById(R.id.openKeyboardSettingsButton);
        openKeyboardSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the virtual keyboard settings
                Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                startActivity(intent);
            }
        });
    }
}
