package com.example.paragonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
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
    ImageView checkoutLogo;
    LinearLayout checkoutLayout, menuLayout, cartLayout;
    Button menuBtn, cartBtn, checkoutBtn, payBtn;
    ImageButton grilledIbtn, friedIbtn, specialIbtn;
    DatabaseReference grilledDatabase, friedDatabase, specialDatabase;
    HashMap<String, String> grillAndPrice = new HashMap<>();
    HashMap<String, String> friedAndPrice = new HashMap<>();
    List cart = new ArrayList<String>();
    Boolean isMenu;
    BigDecimal total;
    TextView textTotal;
    int count = 10;

    HashMap<String, String> specialAndPrice = new HashMap<>();
    List<HashMap<String, String>> listOfGrilledItems = new ArrayList<>();
    List<HashMap<String, String>> listOfFriedItems = new ArrayList<>();
    List<HashMap<String, String>> listOfSpecialItems = new ArrayList<>();
    List usedItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_online);

        total = new BigDecimal("0");
        isMenu = true;

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
        payBtn = findViewById(R.id.payBtn);

        grilledIbtn = (ImageButton) findViewById(R.id.grilledBTN);
        friedIbtn = (ImageButton) findViewById(R.id.friedBTN);
        specialIbtn = (ImageButton) findViewById(R.id.specialBTN);
        checkoutLogo = findViewById(R.id.checkoutLogo);


        textTotal = findViewById(R.id.total);

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
        for (Integer i = 0; i < 7; i++) {
            cart.add("   ");
        }

        final ArrayAdapter adapter = new ArrayAdapter(OrderOnline.this, android.R.layout.simple_list_item_2, android.R.id.text1, cart);
        cartItemsL.setAdapter(adapter);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartLayout.setVisibility(View.VISIBLE);

                menuLayout.setVisibility(View.GONE);
                checkoutLayout.setVisibility(View.GONE);
                checkoutLogo.setVisibility(View.GONE);
                payBtn.setVisibility(View.GONE);
                isMenu = false;
            }
        });



        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartLayout.setVisibility(View.GONE);
                checkoutLayout.setVisibility(View.GONE);
                checkoutLogo.setVisibility(View.GONE);
                payBtn.setVisibility(View.GONE);
                grilledItemsL.setVisibility(View.GONE);
                friedItemsL.setVisibility(View.GONE);
                specialItemsL.setVisibility(View.GONE);
                grilledImage.setVisibility(View.GONE);
                friedImage.setVisibility(View.GONE);
                specialImage.setVisibility(View.GONE);

                menuLayout.setVisibility(View.VISIBLE);
                friedIbtn.setVisibility(View.VISIBLE);
                grilledIbtn.setVisibility(View.VISIBLE);
                specialIbtn.setVisibility(View.VISIBLE);


                isMenu = true;

            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartLayout.setVisibility(View.GONE);
                menuLayout.setVisibility(View.GONE);

                checkoutLayout.setVisibility(View.VISIBLE);
                checkoutLogo.setVisibility(View.VISIBLE);
                payBtn.setVisibility(View.VISIBLE);

                isMenu = false;
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

                isMenu = false;

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

                isMenu = false;
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

                isMenu = false;

            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent create = new Intent(OrderOnline.this, FinalCheckoutScreen.class);

                EditText name = findViewById(R.id.name);
                create.putExtra("name", name.getText().toString());
                create.putExtra("total", total.toString());

                startActivity(create);
            }
        });

        grilledItemsL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = parent.getItemAtPosition(position).toString();
                String s1 = s.substring(s.indexOf("=")+1);
                s1.trim();
                String s2 = s1.substring(0, s1.indexOf(","));
                total = total.add(new BigDecimal(grillAndPrice.get(s2)));
                textTotal.setText(total.toString());
                cart.add(0, s2);
                Toast.makeText(OrderOnline.this, "Added a " + s2 + " to your cart.", Toast.LENGTH_SHORT).show();
                int last = cart.size() - 1;
                if (cart.get(last).equals("   ")) {
                    cart.remove(last);
                }
                final ArrayAdapter adapter = new ArrayAdapter(OrderOnline.this, android.R.layout.simple_list_item_2, android.R.id.text1, cart);
                cartItemsL.setAdapter(adapter);

            }
        });
        friedItemsL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = parent.getItemAtPosition(position).toString();
                String s1 = s.substring(s.indexOf("=")+1);
                s1.trim();
                String s2 = s1.substring(0, s1.indexOf(","));
                total = total.add(new BigDecimal(friedAndPrice.get(s2)));
                textTotal.setText(total.toString());
                cart.add(0, s2);
                Toast.makeText(OrderOnline.this, "Added a " + s2 + " to your cart.", Toast.LENGTH_SHORT).show();
                int last = cart.size() - 1;
                if (cart.get(last).equals("   ")) {
                    cart.remove(last);
                }
                final ArrayAdapter adapter = new ArrayAdapter(OrderOnline.this, android.R.layout.simple_list_item_2, android.R.id.text1, cart);
                cartItemsL.setAdapter(adapter);

            }
        });
        specialItemsL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = parent.getItemAtPosition(position).toString();
                String s1 = s.substring(s.indexOf("=")+1);
                s1.trim();
                String s2 = s1.substring(0, s1.indexOf(","));
                System.out.println(total.toString() + " ================================================================================================================================================");
                total = total.add(new BigDecimal(specialAndPrice.get(s2)));
                textTotal.setText(total.toString());
                cart.add(0, s2);
                Toast.makeText(OrderOnline.this, "Added a " + s2 + " to your cart.", Toast.LENGTH_SHORT).show();
                int last = cart.size() - 1;
                if (cart.get(last).equals("   ")) {
                    cart.remove(last);
                }
                final ArrayAdapter adapter = new ArrayAdapter(OrderOnline.this, android.R.layout.simple_list_item_2, android.R.id.text1, cart);
                cartItemsL.setAdapter(adapter);

            }
        });
        cartItemsL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                System.out.println(s + " +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                ArrayAdapter addedAdapter = new ArrayAdapter(OrderOnline.this, android.R.layout.simple_list_item_2, android.R.id.text1, cart);
                if (!cart.get(position).equals("   ")){
                    cart.remove(position);
                    //Toast.makeText(OrderOnline.this, "Removed " + cart.get(position) + " from your cart.", Toast.LENGTH_SHORT).show();
                    addedAdapter.notifyDataSetChanged();
                    cartItemsL.setAdapter(addedAdapter);
                    if (grillAndPrice.containsKey(s)) {
                        total = total.subtract(new BigDecimal(grillAndPrice.get(s)));
                        total.setScale(2, BigDecimal.ROUND_HALF_UP);
                        textTotal.setText(total.toString());
                    }
                    else if (friedAndPrice.containsKey(s)) {
                        total = total.subtract(new BigDecimal(friedAndPrice.get(s)));
                        total.setScale(2, BigDecimal.ROUND_HALF_UP);
                        textTotal.setText(total.toString());
                    }
                    else if (specialAndPrice.containsKey(s)) {
                        total = total.subtract(new BigDecimal(specialAndPrice.get(s)));
                        total.setScale(2, BigDecimal.ROUND_HALF_UP);
                        textTotal.setText(total.toString());
                    }
                    int last = cart.size();
                    if (last <= 7) {
                        cart.add("   ");
                        final ArrayAdapter adapter = new ArrayAdapter(OrderOnline.this, android.R.layout.simple_list_item_2, android.R.id.text1, cart);
                        cartItemsL.setAdapter(adapter);
                    }

                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (isMenu.equals(true)) {
            finish();
        }
        else {
            cartLayout.setVisibility(View.GONE);
            checkoutLayout.setVisibility(View.GONE);
            checkoutLogo.setVisibility(View.GONE);
            payBtn.setVisibility(View.GONE);
            grilledItemsL.setVisibility(View.GONE);
            friedItemsL.setVisibility(View.GONE);
            specialItemsL.setVisibility(View.GONE);
            grilledImage.setVisibility(View.GONE);
            friedImage.setVisibility(View.GONE);
            specialImage.setVisibility(View.GONE);

            menuLayout.setVisibility(View.VISIBLE);
            friedIbtn.setVisibility(View.VISIBLE);
            grilledIbtn.setVisibility(View.VISIBLE);
            specialIbtn.setVisibility(View.VISIBLE);

            isMenu = true;
        }

    }
}