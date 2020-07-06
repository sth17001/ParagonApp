package com.example.paragonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class loggedIn extends AppCompatActivity {
    ImageButton btnOrderOnline, btnDailySpecial;
    Button managerbtn;
    ImageView paragonLogo;
    String username;
    String userType;
    Boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        isAdmin = false;

        btnOrderOnline = (ImageButton)findViewById(R.id.orderOnline);
        btnDailySpecial = (ImageButton)findViewById(R.id.dailySpecials);
        managerbtn = (Button)findViewById(R.id.managerbtn);
        paragonLogo = (ImageView)findViewById(R.id.paragonlogo);

        userType = "";
        username = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userType = extras.getString("user_type");
            username = extras.getString("user_name");
            System.out.println("Username with the intent: " + username);
        }

        if (userType.equals("admin")) {
            System.out.println("MANAGE ACCESS GRANTED");
            isAdmin = true;
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
                create.putExtra("isAdmin", isAdmin);
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
    @Override
    public void onBackPressed() {


        Intent create = new Intent(loggedIn.this, Home.class);
        startActivity(create);

    }
}