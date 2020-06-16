package com.example.paragonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OrderOnline extends AppCompatActivity {
    List emptyList = new ArrayList<String>();
    ListView cartItemsL, grilledItemsL, friedItemsL, specialItemsL;
    ImageButton grilledImage, friedImage, specialImage;
    LinearLayout checkoutLayout, menuLayout, cartLayout;
    Button menuBtn, cartBtn, checkoutBtn;
    ImageButton grilledIbtn, friedIbtn, specialIbtn;
    DatabaseReference grilledDatabase, friedDatabase, specialDatabase;
    HashMap<String, String> grillAndPrice = new HashMap<>();
    HashMap<String, String> friedAndPrice = new HashMap<>();

    HashMap<String, String> specialAndPrice = new HashMap<>();
    List<HashMap<String, String>> listOfGrilledItems = new ArrayList<>();
    List<HashMap<String, String>> listOfFriedItems = new ArrayList<>();
    List<HashMap<String, String>> listOfSpecialItems = new ArrayList<>();
    List usedItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_online);

        cartItemsL = (ListView)findViewById(R.id.cartItems);
        grilledItemsL = (ListView)findViewById(R.id.grilledListView);
        friedItemsL = (ListView)findViewById(R.id.friedListView);
        specialItemsL = (ListView)findViewById(R.id.specialListView);

        grilledImage = (ImageButton)findViewById(R.id.grilledImage);
        friedImage = (ImageButton)findViewById(R.id.friedImage);
        specialImage = (ImageButton)findViewById(R.id.specialImage);

        checkoutLayout = (LinearLayout) findViewById(R.id.checkout);
        menuLayout = (LinearLayout)findViewById(R.id.menu);
        cartLayout = (LinearLayout)findViewById(R.id.cart);

        cartBtn = (Button) findViewById(R.id.cartBTN);
        menuBtn = (Button) findViewById(R.id.menuBTN);
        checkoutBtn = (Button) findViewById(R.id.checkoutBTN);

        grilledIbtn = (ImageButton) findViewById(R.id.grilledBTN);
        friedIbtn = (ImageButton) findViewById(R.id.friedBTN);
        specialIbtn = (ImageButton) findViewById(R.id.specialBTN);

        //access database for items
        grilledDatabase = FirebaseDatabase.getInstance().getReference().child("GrilledItems");
        friedDatabase = FirebaseDatabase.getInstance().getReference().child("FriedItems");
        specialDatabase = FirebaseDatabase.getInstance().getReference().child("SpecialItems");

        //Load GrilledItems
        grilledDatabase.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Item item = new Item (data.child("name").getValue().toString(), data.child("price").getValue().toString());
                    grillAndPrice.put(item.getName(), item.getPrice());
                }

                SimpleAdapter adapter = new SimpleAdapter(OrderOnline.this, listOfGrilledItems, R.layout.list_view, new String[]{"1", "2"}, new int[]{R.id.text1, R.id.text2});
                Iterator it = grillAndPrice.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    resultsMap.put("1", pair.getKey().toString());
                    resultsMap.put("2", "$"+pair.getValue().toString());
                    listOfGrilledItems.add(resultsMap);

                }
                grilledItemsL.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        friedDatabase.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Item item = new Item (data.child("name").getValue().toString(), data.child("price").getValue().toString());
                    friedAndPrice.put(item.getName(), item.getPrice());
                }

                SimpleAdapter adapter = new SimpleAdapter(OrderOnline.this, listOfFriedItems, R.layout.list_view, new String[]{"1", "2"}, new int[]{R.id.text1, R.id.text2});
                Iterator it = friedAndPrice.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    resultsMap.put("1", pair.getKey().toString());
                    resultsMap.put("2", "$"+pair.getValue().toString());
                    listOfFriedItems.add(resultsMap);

                }
                friedItemsL.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        specialDatabase.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Item item = new Item (data.child("name").getValue().toString(), data.child("price").getValue().toString());
                    specialAndPrice.put(item.getName(), item.getPrice());
                }

                SimpleAdapter adapter = new SimpleAdapter(OrderOnline.this, listOfSpecialItems, R.layout.list_view, new String[]{"1", "2"}, new int[]{R.id.text1, R.id.text2});
                Iterator it = specialAndPrice.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    resultsMap.put("1", pair.getKey().toString());
                    resultsMap.put("2", "$"+pair.getValue().toString());
                    listOfSpecialItems.add(resultsMap);

                }
                specialItemsL.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        //TODO change this
        for (Integer i = 0; i < 10; i++) {
            emptyList.add("   ");
        }

        final ArrayAdapter adapter = new ArrayAdapter(OrderOnline.this, android.R.layout.simple_list_item_2, android.R.id.text1, emptyList);
        Collections.sort(emptyList);
        cartItemsL.setAdapter(adapter);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartLayout.setVisibility(View.VISIBLE);
                menuLayout.setVisibility(View.GONE);
                checkoutLayout.setVisibility(View.GONE);
            }
        });

        OrderOnline.onBackPressed()

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartLayout.setVisibility(View.GONE);
                menuLayout.setVisibility(View.VISIBLE);
                checkoutLayout.setVisibility(View.GONE);
                friedIbtn.setVisibility(View.VISIBLE);
                grilledIbtn.setVisibility(View.VISIBLE);
                specialIbtn.setVisibility(View.VISIBLE);
                grilledItemsL.setVisibility(View.GONE);
                friedItemsL.setVisibility(View.GONE);
                specialItemsL.setVisibility(View.GONE);
                grilledImage.setVisibility(View.GONE);
                friedImage.setVisibility(View.GONE);
                specialImage.setVisibility(View.GONE);

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
                specialIbtn.setVisibility(View.GONE);
                friedIbtn.setVisibility(View.GONE);
                grilledIbtn.setVisibility(View.GONE);

                specialImage.setVisibility(View.VISIBLE);
                specialItemsL.setVisibility(View.VISIBLE);

            }
        });

        friedIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialIbtn.setVisibility(View.GONE);
                friedIbtn.setVisibility(View.GONE);
                grilledIbtn.setVisibility(View.GONE);

                friedImage.setVisibility(View.VISIBLE);
                friedItemsL.setVisibility(View.VISIBLE);
            }
        });
        grilledIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialIbtn.setVisibility(View.GONE);
                friedIbtn.setVisibility(View.GONE);
                grilledIbtn.setVisibility(View.GONE);

                grilledImage.setVisibility(View.VISIBLE);
                grilledItemsL.setVisibility(View.VISIBLE);

            }
        });
    }
}