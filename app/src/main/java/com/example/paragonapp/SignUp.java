package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_LONG;

public class SignUp extends AppCompatActivity {
    EditText getUsername, getPassword, getEmail, getPasswordConfirm;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");
    }

    public void signUp(View view) {
        getEmail = (EditText) findViewById(R.id.emailEdit);
        getUsername = (EditText) findViewById(R.id.usernameEdit2);
        getPassword = (EditText) findViewById(R.id.passwordEdit2);
        getPasswordConfirm = (EditText) findViewById(R.id.passwordEdit3);
        String email = getEmail.getText().toString();
        String username = getUsername.getText().toString();
        String password = getPassword.getText().toString();
        String passwordConfirm = getPasswordConfirm.getText().toString();

        User newUser = new User(email, username, password);
        System.out.println(newUser.getEmail() + ", " + newUser.getUsername() + ", "
                + newUser.getPassword());

        if (canSignup(newUser, passwordConfirm) == true) {
            userRef.child(newUser.getUsername() + "ID").setValue(newUser);
            System.out.println("Signed up: " + newUser.getUsername());
            Toast.makeText(SignUp.this, "Welcome " + newUser.getUsername(), LENGTH_LONG).show();
        }
    }

    public boolean canSignup(User newUser, String passwordConfirm) {
        if (!newUser.getUsername().equals("")
                && !newUser.getPassword().equals("")
                && !newUser.getEmail().equals("")) {
            if (passwordConfirm.equals(newUser.getPassword())) {
                return true;
            } else {
                System.out.println("Password does not match.");
                Toast.makeText(SignUp.this, "Password does not match.", LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(SignUp.this, "Please enter all the credentials.", LENGTH_LONG).show();
            return false;
        }

    }
}