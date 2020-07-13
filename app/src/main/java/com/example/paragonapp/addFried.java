package com.example.paragonapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addFried extends AppCompatActivity {
    Button addTool;
    EditText nameT, priceT;
    Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fried);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("FriedItems");

        addTool =(Button)findViewById(R.id.addButtonTool);
        nameT=(EditText) findViewById(R.id.ItemName);
        priceT =(EditText) findViewById(R.id.ItemPrice);
        count = 0;


        DatabaseReference tool = FirebaseDatabase.getInstance().getReference().child("FriedItems");
        tool.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Item tool = new Item (data.child("name").getValue().toString(), data.child("price").getValue().toString());

                    count=Integer.parseInt(data.getKey().toString());
                    System.out.println(count.toString() + " "+data.getKey()+ " ==============================================================================================================");
                }
                count++;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        addTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameT.getText().toString().equals("")|| priceT.getText().toString().equals("")) {
                    Toast.makeText(addFried.this, "Missing a name or price", Toast.LENGTH_SHORT).show();
                }
                else {

                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(nameT.getText().toString()).exists()) {
                                Toast.makeText(addFried.this, "Item already exists", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Item employee = new Item(nameT.getText().toString(), priceT.getText().toString());
                                table_user.child(count.toString()).setValue(employee);
                                Toast.makeText(addFried.this, "New Item added", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

        });
    }
}