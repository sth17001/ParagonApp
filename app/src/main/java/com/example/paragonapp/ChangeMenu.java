package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChangeMenu extends AppCompatActivity {
    DatabaseReference friedItemsRef, grilledItemsRef, specialItemsRef;
    ArrayList<Item> friedArrayList = new ArrayList<Item>();
    ArrayList<Item> grilledArrayList = new ArrayList<Item>();
    ArrayList<Item> specialArrayList = new ArrayList<Item>();
    Button friedlistbtn;
    Button grilledlistbtn;
    Button speciallistbtn;
    ListView friedList;
    ListView grilledList;
    ListView specialList;
    ArrayAdapter friedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_menu);

        friedlistbtn = (Button) findViewById(R.id.friedlistbtn);
        grilledlistbtn = (Button) findViewById(R.id.grilledlistbtn);
        speciallistbtn = (Button) findViewById(R.id.speciallistbtn);

        friedList = (ListView) findViewById(R.id.friedList);
        grilledList = (ListView) findViewById(R.id.grilledList);
        specialList = (ListView) findViewById(R.id.specialList);

        friedlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayItems("fried");
                friedlistbtn.setVisibility(View.GONE);
                grilledlistbtn.setVisibility(View.GONE);
                speciallistbtn.setVisibility(View.GONE);
                friedList.setVisibility(View.VISIBLE);
                grilledList.setVisibility(View.GONE);
                specialList.setVisibility(View.GONE);
            }
        });

        grilledlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayItems("grilled");
                friedlistbtn.setVisibility(View.GONE);
                grilledlistbtn.setVisibility(View.GONE);
                speciallistbtn.setVisibility(View.GONE);
                friedList.setVisibility(View.GONE);
                grilledList.setVisibility(View.VISIBLE);
                specialList.setVisibility(View.GONE);
            }
        });

        speciallistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayItems("special");
                friedlistbtn.setVisibility(View.GONE);
                grilledlistbtn.setVisibility(View.GONE);
                speciallistbtn.setVisibility(View.GONE);
                friedList.setVisibility(View.GONE);
                grilledList.setVisibility(View.GONE);
                specialList.setVisibility(View.VISIBLE);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        friedItemsRef = database.getReference("FriedItems");
        grilledItemsRef = database.getReference("GrilledItems");
        specialItemsRef = database.getReference("SpecialItems");

        download downloadThread = new download();
        downloadThread.run();

    }

    public class download extends Thread {
        public void run() {
            downloadItems();
        }
    }

    public void displayItems(String type) {
        if (type == "fried") {
            System.out.println("ARRAYLIST SIZE - " + friedArrayList.size());
            for (int i = 0; i < friedArrayList.size(); i++) {
                System.out.println(friedArrayList.get(i).getName());
            }
        } else if (type == "grilled") {
            System.out.println("ARRAYLIST SIZE - " + grilledArrayList.size());
            for (int i = 0; i < grilledArrayList.size(); i++) {
                System.out.println(grilledArrayList.get(i).getName());
            }
        } else if (type == "special") {
            System.out.println("ARRAYLIST SIZE - " + specialArrayList.size());
            for (int i = 0; i < specialArrayList.size(); i++) {
                System.out.println(specialArrayList.get(i).getName());
            }
        }
    }

    public void downloadItems() {
        friedItemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //int i = 0;
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    String name = userSnap.child("name").getValue().toString();
                    String price = userSnap.child("price").getValue().toString();

                    Item item = new Item(name, price);

                    friedArrayList.add(item);
                    //System.out.println("Downloading... " + grilledArrayList.get(i).getName());
                    //i++;
                }
                System.out.println(friedArrayList.size() + " items downloaded.");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value.");
            }
        });

        grilledItemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //int i = 0;
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    String name = userSnap.child("name").getValue().toString();
                    String price = userSnap.child("price").getValue().toString();

                    Item item = new Item(name, price);

                    grilledArrayList.add(item);
                    //System.out.println("Downloading... " + grilledArrayList.get(i).getName());
                    //i++;
                }
                System.out.println(grilledArrayList.size() + " items downloaded.");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value.");
            }
        });

        specialItemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //int i = 0;
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    String name = userSnap.child("name").getValue().toString();
                    String price = userSnap.child("price").getValue().toString();

                    Item item = new Item(name, price);

                    specialArrayList.add(item);
                    //System.out.println("Downloading... " + specialArrayList.get(i).getName());
                    //i++;
                }
                System.out.println(specialArrayList.size() + " items downloaded.");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value.");
            }
        });
    }
}