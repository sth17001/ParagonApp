package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OrderOnline extends AppCompatActivity {
    List emptyList = new ArrayList<String>();
    ListView cartItemsL;
    LinearLayout checkoutLayout, menuLayout, cartLayout;
    Button menuBtn, cartBtn, checkoutBtn;
    ImageButton grilledIbtn, friedIbtn, sandwichIbtn, specialIbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_online);

        cartItemsL = (ListView)findViewById(R.id.cartItems);

        checkoutLayout = (LinearLayout) findViewById(R.id.checkout);
        menuLayout = (LinearLayout)findViewById(R.id.menu);
        cartLayout = (LinearLayout)findViewById(R.id.cart);

        cartBtn = (Button) findViewById(R.id.cartBTN);
        menuBtn = (Button) findViewById(R.id.menuBTN);
        checkoutBtn = (Button) findViewById(R.id.checkoutBTN);

        grilledIbtn = (ImageButton) findViewById(R.id.grilledBTN);
        friedIbtn = (ImageButton) findViewById(R.id.friedBTN);
        sandwichIbtn = (ImageButton) findViewById(R.id.sandwichBTN);
        specialIbtn = (ImageButton) findViewById(R.id.specialBTN);


        for (Integer i = 0; i < 10; i++) {
            emptyList.add("   ");
        }

        final ArrayAdapter adapter = new ArrayAdapter(OrderOnline.this, android.R.layout.simple_list_item_2, android.R.id.text1, emptyList);
        java.util.Collections.sort(emptyList);
        cartItemsL.setAdapter(adapter);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartLayout.setVisibility(View.VISIBLE);
                menuLayout.setVisibility(View.GONE);
                checkoutLayout.setVisibility(View.GONE);
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartLayout.setVisibility(View.GONE);
                menuLayout.setVisibility(View.VISIBLE);
                checkoutLayout.setVisibility(View.GONE);
                sandwichIbtn.setVisibility(View.VISIBLE);
                friedIbtn.setVisibility(View.VISIBLE);
                grilledIbtn.setVisibility(View.VISIBLE);
                specialIbtn.setVisibility(View.VISIBLE);

            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartLayout.setVisibility(View.GONE);
                menuLayout.setVisibility(View.GONE);
                checkoutLayout.setVisibility(View.VISIBLE);
            }
        });

        specialIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sandwichIbtn.setVisibility(View.GONE);
                friedIbtn.setVisibility(View.GONE);
                grilledIbtn.setVisibility(View.GONE);
            }
        });
        sandwichIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialIbtn.setVisibility(View.GONE);
                friedIbtn.setVisibility(View.GONE);
                grilledIbtn.setVisibility(View.GONE);
            }
        });
        friedIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sandwichIbtn.setVisibility(View.GONE);
                specialIbtn.setVisibility(View.GONE);
                grilledIbtn.setVisibility(View.GONE);
            }
        });
        grilledIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sandwichIbtn.setVisibility(View.GONE);
                friedIbtn.setVisibility(View.GONE);
                specialIbtn.setVisibility(View.GONE);
            }
        });
    }
}