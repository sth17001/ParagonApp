package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OrderOnline extends AppCompatActivity {
    List emptyList = new ArrayList<String>();
    ListView cartItemsL;
    LinearLayout checkoutLayout, menuLayout, cartLayout;
    Button menuBtn, cartBtn, checkoutBtn;

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
    }
}