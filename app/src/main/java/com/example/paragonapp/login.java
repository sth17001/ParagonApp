package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logIn(View view) {
        EditText getUsername = (EditText) findViewById(R.id.editText1);
        EditText getPassword = (EditText) findViewById(R.id.editText2);
        String emailOrUsername = getUsername.getText().toString();
        String password = getPassword.getText().toString();

    }
}