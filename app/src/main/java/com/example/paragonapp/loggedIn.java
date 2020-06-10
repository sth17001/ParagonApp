package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class loggedIn extends AppCompatActivity {
ImageButton btnOrderOnline, btnDailySpecial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        btnOrderOnline = (ImageButton)findViewById(R.id.orderOnline);
        btnDailySpecial = (ImageButton)findViewById(R.id.dailySpecials);

        btnOrderOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(loggedIn.this, OrderOnline.class);
                startActivity(create);
            }
        });

        btnDailySpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(loggedIn.this, DailySpecials.class);
                startActivity(create);
            }
        });


    }
}