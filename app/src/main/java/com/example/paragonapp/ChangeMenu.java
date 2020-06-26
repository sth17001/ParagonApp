package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChangeMenu extends AppCompatActivity {
    DatabaseReference friedItemsRef;// grilledItemsRef, specialItemsRef;
    ArrayList<Item> friedArrayList = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_menu);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        friedItemsRef = database.getReference("FriedItems");
        //grilledItemsRef = database.getReference("GrilledItems");
        //specialItemsRef = database.getReference("SpecialItems");

        downloadItems();

        //System.out.println("array size: " + friedArrayList.size());
        displaySize();
    }

    public void downloadItems() {
        friedItemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    String name = userSnap.child("name").getValue().toString();
                    String price = userSnap.child("price").getValue().toString();

                    Item item = new Item(name, price);

                    friedArrayList.add(item);
                    System.out.println("Downloading... " + friedArrayList.size());
                }
                System.out.println(friedArrayList.size() + " items downloaded.");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value.");
            }
        });
    }
    public void displaySize() {
        System.out.println("array size: " + friedArrayList.size());
    }
}