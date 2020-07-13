package com.example.paragonapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DeleteFried extends AppCompatActivity {
    DatabaseReference tool;
    HashMap<String, String> toolAndPrice = new HashMap<>();
    HashMap<String, String> toolAndKey = new HashMap<>();
    List<HashMap<String, String>> listOfTools = new ArrayList<>();
    ListView toolListView;
    String deleteName, deleteID;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_fried);
        deleteName = "";

        toolListView = (ListView)findViewById(R.id.deleteToolView);

        //Load tools
        tool = FirebaseDatabase.getInstance().getReference().child("FriedItems");
        tool.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Item tool = new Item (data.child("name").getValue().toString(), data.child("price").getValue().toString(), data.getKey().toString());
                    toolAndPrice.put(tool.getName(), tool.getPrice());
                    toolAndKey.put(tool.getName(), tool.getId());
                }

                adapter = new SimpleAdapter(DeleteFried.this, listOfTools, R.layout.listview2, new String[]{"1", "2"}, new int[]{R.id.text1, R.id.text2});
                Iterator it = toolAndPrice.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    resultsMap.put("1", pair.getKey().toString());
                    resultsMap.put("2", "$"+pair.getValue().toString());
                    listOfTools.add(resultsMap);

                }
                //final ArrayAdapter adapter = new ArrayAdapter(CreateInvoice.this, android.R.layout.simple_list_item_2, android.R.id.text1, toolsArrayList);
                toolListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //Enable touch controls for lists
        toolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Tool tool = (Tool) parent.getItemAtPosition(position);
                String s = parent.getItemAtPosition(position).toString();
                String s1 = s.substring(s.indexOf("=")+1);
                s1.trim();
                String s2 = s1.substring(0, s1.indexOf(","));
                deleteName = listOfTools.get(position).get("1");
                deleteID = toolAndKey.get(deleteName);
                System.out.println(deleteName + " ======================================================================");
                listOfTools.remove(position);
                adapter.notifyDataSetChanged();

                tool.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            if (data.getKey().toString().equals(deleteID)) {
                                data.getRef().removeValue();
                                Toast.makeText(DeleteFried.this, "Removed Item "+deleteName, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}