package com.example.imagepro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Sign_Guide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        String[] buttonNames = {
                "Alphabets", "Numbers", "Question Words", "Health and Body",
                "Greetings and Phrases", "Emoji Combinations", "Day and Time of Day",
                "Colors", "Family Members and People", "Food and Meals",
                "Time", "Button", "Button", "Button", "Button", "Button", "Button"
        };

        int[] buttonColors = {
                // Define your custom colors here
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color1)
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ButtonAdapter adapter = new ButtonAdapter(buttonNames, buttonColors);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ButtonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        // Open AlphabetsActivity when "Alphabets" button is clicked
                        startActivity(new Intent(Sign_Guide.this, PlayVideo.class));
                        break;
                    // Handle other button clicks similarly
                }
            }
        });
    }
}
