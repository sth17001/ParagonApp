package com.example.paragonapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class login extends AppCompatActivity {
    EditText getUsername, getPassword;
    EditText loadUsername, loadPassword;
    DatabaseReference userRef;
    ArrayList<User> userArrayList = new ArrayList<User>();
    Boolean rememberChecked;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        User savedUser = new User();
        loadUser(savedUser);
        System.out.println("Saved User: " + savedUser.getUsername());

        if (savedUser.getUsername() != "null") {
            loadUsername = (EditText) findViewById(R.id.usernameEdit);
            loadPassword = (EditText) findViewById(R.id.passwordEdit);
            loadUsername.setText(savedUser.getUsername());
            loadPassword.setText(savedUser.getPassword());
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");
        downloadUsers();
    }

    public void signUpActivity(View view) {
        Intent create = new Intent(login.this, SignUp.class);
        startActivity(create);
    }

    public void logIn(View view) throws InterruptedException {
        userType = "user";
        rememberChecked = ((CheckBox) findViewById(R.id.rememberCheck)).isChecked();

        getUsername = (EditText) findViewById(R.id.usernameEdit);
        getPassword = (EditText) findViewById(R.id.passwordEdit);

        User currentUser = new User(getUsername.getText().toString(), getPassword.getText().toString());
        System.out.println(currentUser.getUsername() + ", " + currentUser.getPassword());

        if (canLogin(currentUser)) {
            if (rememberChecked) {
                saveUser(currentUser);
                System.out.println("user saved: " + currentUser.getUsername());
            }

            Intent create = new Intent(login.this, loggedIn.class);

            if (isAdmin(currentUser) == true) {
                userType = "admin";
                System.out.println("MANAGER ACCESS REQUESTED");
            }

            create.putExtra("user_type", userType);
            create.putExtra("user_name", currentUser.getUsername());

            startActivity(create);
            //Toast.makeText(login.this, "Logged in succesfully!", LENGTH_LONG).show();
        }

    }
    public void downloadUsers() {

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
        boolean userFound = false;
        boolean canLogin = false;
        for (int i = 0; i < userArrayList.size(); i++) {
            if (currentUser.getUsername().equals(userArrayList.get(i).getUsername())) {
                userFound = true;
                if (currentUser.getPassword().equals(userArrayList.get(i).getPassword())) {
                    canLogin = true;
                    Toast.makeText(login.this, "Logged in as " + currentUser.getUsername(), LENGTH_LONG).show();
                } else {
                    System.out.println("Incorrect password!");
                    Toast.makeText(login.this, "Incorrect username/password.", LENGTH_LONG).show();
                }
            }
        }
        if (userFound == false) {
            System.out.println("Invalid user!");
            Toast.makeText(login.this, "Incorrect username/password.", LENGTH_LONG).show();
        }
        return canLogin;
    }

    public boolean isAdmin(User currentUser) {
        boolean isAdmin = false;
        if (currentUser.getUsername().equals("admin")) {
            isAdmin = true;
        }
        return isAdmin;
    }

    public void saveUser(User currentUser) {
        String preferences = null;
        SharedPreferences sharedpreferences = getPreferences(0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username", currentUser.getUsername());
        editor.putString("password", currentUser.getPassword());
        editor.commit();
    }

    public void loadUser(User currentUser) {
        String preferences = null;
        SharedPreferences sharedpreferences = getPreferences(0);
        currentUser.setUsername(sharedpreferences.getString("username", "null"));
        currentUser.setPassword(sharedpreferences.getString("password", "null"));
    }

}