package com.example.paragonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class login extends AppCompatActivity {
    EditText getUsername, getPassword;
    String emailOrUsername, password;
    DatabaseReference table_user;
    boolean loggedin;
    Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.loginBTN);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsername = (EditText) findViewById(R.id.usernameEdit);
                getPassword = (EditText) findViewById(R.id.passwordEdit);
                emailOrUsername = getUsername.getText().toString();
                password = getPassword.getText().toString();
                System.out.println(password+ " ========================================================================");
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(getUsername.getText().toString()).exists()) {
                            User user = dataSnapshot.child.getValue(User.class);
                            System.out.println(user.getPassword()+ " ========================================================================");
                            /*if (user.getPassword().equals(password)) {
                                loggedin = true;
                                Toast.makeText(login.this, "Logged in", LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(login.this, "Login failed", LENGTH_LONG).show();
                            }*/
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

}