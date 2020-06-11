package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DailySpecials extends AppCompatActivity {
Button dailyButton,weeklyButton;
ImageView daily,paragonweekly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_specials);

        //Buttons for Switching between daily and weekly specials
        dailyButton = (Button)findViewById(R.id.dailyButton);
        weeklyButton = (Button)findViewById(R.id.weeklyButton);

        // Images for daily and weekly specials
        daily = (ImageView)findViewById(R.id.dailyPic);
        paragonweekly = (ImageView)findViewById(R.id.weeklyPic);

        dailyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            daily.setVisibility(View.GONE);
            paragonweekly.setVisibility(View.VISIBLE);
                // Code here executes on main thread after user presses button
            }
        });
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                daily.setVisibility(View.VISIBLE);
                paragonweekly.setVisibility(View.GONE);
                // Code here executes on main thread after user presses button
            }
        });









    }
}