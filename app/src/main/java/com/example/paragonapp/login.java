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
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class login extends AppCompatActivity {
    EditText getUsername, getPassword;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");
    }

    public void logIn(View view) throws InterruptedException {
        getUsername = (EditText) findViewById(R.id.usernameEdit);
        getPassword = (EditText) findViewById(R.id.passwordEdit);

        final User user = new User(getUsername.getText().toString(), getPassword.getText().toString());
        System.out.println(user.getUsername() + ", " + user.getPassword());

        /*userRef.child("1").child("password").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                System.out.println("Value is: " + value);
                System.out.println("userclass password is: " + user.getPassword());
                if(user.getPassword().equals(value)) {
                    canLogin = true;
                    System.out.println("can login!");
                } else {
                    System.out.println("cannot login!");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value.");
            }
        });*/

        if (user.accessFirebase(userRef) == true) {
            Toast.makeText(login.this, "Logged in succesfully!", LENGTH_LONG).show();
        } else {
            Toast.makeText(login.this, "Login FAILED!", LENGTH_LONG).show();
        }

    }
    public boolean accessFirebase(DatabaseReference userRef) {

        userRef.child("1").child("password").addValueEventListener(new ValueEventListener() {
            boolean login1 = login;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    String username = userSnap.child("username").getValue().toString();
                    String password = userSnap.child("password").getValue().toString();

                    User user = new User(username, password);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value.");
            }
        });
        if (login == true) {
            System.out.println("login is true");
        } else {
            System.out.println("login is false");
        }
        return login;
    }
}