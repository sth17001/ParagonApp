package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DailySpecials extends AppCompatActivity {
Button dailyButton,weeklyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_specials);

        dailyButton = (Button)findViewById(R.id.dailyButton);
        weeklyButton = (Button)findViewById(R.id.weeklyButton);

        dailyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Code here executes on main thread after user presses button
            }
        });
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Code here executes on main thread after user presses button
            }
        });









    }
}