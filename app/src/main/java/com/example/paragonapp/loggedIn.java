package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.core.view.Change;

public class loggedIn extends AppCompatActivity {
    ImageButton btnOrderOnline, btnDailySpecial;
    Button managerbtn;
    ImageView paragonLogo;
    String username;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        btnOrderOnline = (ImageButton)findViewById(R.id.orderOnline);
        btnDailySpecial = (ImageButton)findViewById(R.id.dailySpecials);
        managerbtn = (Button)findViewById(R.id.managerbtn);
        paragonLogo = (ImageView)findViewById(R.id.paragonlogo);

        Intent intent = getIntent();
        userType = intent.getStringExtra("user_type");
        username = intent.getStringExtra("user_name");
        System.out.println("Username with the intent: " + username);

        if (userType.equals("admin")) {
            System.out.println("MANAGE ACCESS GRANTED");
            paragonLogo.setVisibility(View.GONE);
            managerbtn.setVisibility(View.VISIBLE);
        }

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

        managerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(loggedIn.this, ChangeMenu.class);
                startActivity(create);
            }
        });


    }
}