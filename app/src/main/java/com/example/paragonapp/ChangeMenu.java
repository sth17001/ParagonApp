package com.example.paragonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeMenu extends AppCompatActivity {
    Button btnNewGrill, btnNewFried, btnNewSpecial, btnDeleteGrill, btnDeleteFried, btnDeleteSpecial;
    LinearLayout buttons, addToolL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_menu);

        btnDeleteGrill = (Button)findViewById(R.id.removeGrillItem);
        btnDeleteFried = (Button)findViewById(R.id.removeFriedItem);
        btnDeleteSpecial = (Button)findViewById(R.id.removeSpecialItem);
        btnNewGrill = (Button)findViewById(R.id.addGrillItem);
        btnNewFried = (Button)findViewById(R.id.addFriedItem);
        btnNewSpecial = (Button)findViewById(R.id.addSpecialItem);
        buttons = (LinearLayout)findViewById(R.id.managerOptions);
        addToolL = (LinearLayout)findViewById(R.id.addAtool);


        btnNewGrill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(ChangeMenu.this, addGrill.class);
                startActivity(create);
            }
        });
        btnNewFried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(ChangeMenu.this, addFried.class);
                startActivity(create);
            }
        });
        btnNewSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(ChangeMenu.this, addSpecial.class);
                startActivity(create);
            }
        });
        btnDeleteGrill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(ChangeMenu.this, DeleteGrill.class);
                startActivity(create);
            }
        });
        btnDeleteFried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(ChangeMenu.this, DeleteFried.class);
                startActivity(create);
            }
        });
        btnDeleteSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(ChangeMenu.this, DeleteSpecial.class);
                startActivity(create);
            }
        });


    }
}