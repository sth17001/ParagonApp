package com.example.paragonapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class DailySpecials extends AppCompatActivity {
Button dailyButton,weeklyButton,editDailySpecial,editWeeklySpecial;
PhotoView daily,paragonweekly;

private static final int GalleryPic = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_specials);

        //Buttons for Displaying Daily/Weekly specials
        dailyButton = (Button)findViewById(R.id.dailyButton);
        weeklyButton = (Button)findViewById(R.id.weeklyButton);

        // Buttons for Editing Daily/Weekly Specials
        editDailySpecial = (Button)findViewById(R.id.editDailySpecial);
        editWeeklySpecial = (Button)findViewById(R.id.editWeeklySpecial);

        editWeeklySpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GalleryPic);
            }
        });








        // Images for daily and weekly specials
        daily = (PhotoView) findViewById(R.id.dailyPic);
        paragonweekly = (PhotoView) findViewById(R.id.weeklyPic);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
    }


}