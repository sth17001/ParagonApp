package com.example.paragonapp;

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

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class login extends AppCompatActivity {
    EditText getUsername, getPassword;
    DatabaseReference userRef;
    ArrayList<User> userArrayList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");
        downloadFirebase();
    }

    public void logIn(View view) throws InterruptedException {
        getUsername = (EditText) findViewById(R.id.usernameEdit);
        getPassword = (EditText) findViewById(R.id.passwordEdit);
        int userNum = 0;

        User currentUser = new User(getUsername.getText().toString(), getPassword.getText().toString());
        System.out.println(currentUser.getUsername() + ", " + currentUser.getPassword());

        System.out.println(userArrayList.get(3).getUsername() + ", " + userArrayList.get(3).getPassword());

        for (int i = 0; i <= userNum; i++) {
            if (currentUser.getUsername().equals(userArrayList.get(i).getUsername())) {
                if (currentUser.getPassword().equals(userArrayList.get(i).getPassword())) {
                    Toast.makeText(login.this, "Logged in succesfully!", LENGTH_LONG).show();
                }
            }
        }

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
        });

        if (currentUser.accessFirebase(userRef) == true) {
            Toast.makeText(login.this, "Logged in succesfully!", LENGTH_LONG).show();
        } else {
            Toast.makeText(login.this, "Login FAILED!", LENGTH_LONG).show();
        }*/

    }
    public void downloadFirebase() {

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    String username = userSnap.child("username").getValue().toString();
                    String password = userSnap.child("password").getValue().toString();

                    User user = new User(username, password);

                    userArrayList.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value.");
            }
        });
    }
    public boolean canLogin(User currentUser) {
        int userNum = 0;
        for (int i = 0; i <= userNum; i++) {
            if (currentUser.getUsername().equals(userArrayList.get(i).getUsername())) {
                if (currentUser.getPassword().equals(userArrayList.get(i).getPassword())) {
                    return true;
                    Toast.makeText(login.this, "Logged in succesfully!", LENGTH_LONG).show();
                } else {
                    return false;
                    System.out.println("incorrect password!");
                    Toast.makeText(login.this, "Incorrect username/password.", LENGTH_LONG).show();
                }
            }
        }
    }
}