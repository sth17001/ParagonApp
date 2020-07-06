package com.example.paragonapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FinalCheckoutScreen extends AppCompatActivity {

    String name, total, tempName, newInvoice, invoiceNumber;
    TextView totalView;
    BigDecimal totalBD, taxAmountBD, finalTotalBD;
    Button placeOrder;
    List cartOrder = new ArrayList<String>();
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_checkout_screen);

        placeOrder = findViewById(R.id.finalBtn);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            total = extras.getString("total");
            cartOrder =  extras.getStringArrayList("order");
        }



        totalView = findViewById(R.id.finalTotal);

        totalBD = new BigDecimal(total);
        taxAmountBD = totalBD.multiply(new BigDecimal(".06"));
        taxAmountBD = taxAmountBD.setScale(2, taxAmountBD.ROUND_HALF_UP);;
        totalBD.setScale(2, totalBD.ROUND_HALF_UP);

        finalTotalBD = new BigDecimal("0");
        finalTotalBD = finalTotalBD.add(totalBD);

        finalTotalBD = finalTotalBD.add(taxAmountBD);
        finalTotalBD.setScale(2, finalTotalBD.ROUND_HALF_UP);
        totalBD.setScale(2, taxAmountBD.ROUND_HALF_UP);

        if (!name.equals(null)) {
            name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
        if (name.contains(" "))
        {
            tempName  = name + "3";
        }
        else {
            tempName  = name + " 3";
        }

        StringBuilder str = new StringBuilder(tempName);
        int startIdx = str.indexOf(" ");
        int endIdx = str.indexOf("3");
        str.replace(++startIdx, ++endIdx, "");
        tempName = str.toString();
        tempName = tempName.substring(0, tempName.length() - 1);

        totalView.setText("======================="+"\n" + tempName + ",\n\nPlease review the details below.\n\n" +
                " \nTotal Before Tax: $" + totalBD.toString()+"\nTax Amount: $"+ taxAmountBD.toString() + "\n======================="+ "\n\nTotal: $"+ finalTotalBD.toString() );

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        final DatabaseReference table_invoice = database2.getReference("Orders");


        final ProgressDialog mDialog = new ProgressDialog(FinalCheckoutScreen.this);
        mDialog.setMessage("Generating invoice number");
        mDialog.show();
        table_invoice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //search for the next empty number
                Integer count = 1;
                while (dataSnapshot.child(count.toString()).exists())
                {
                    count += 1;
                }
                mDialog.dismiss();

                newInvoice =  count.toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_order = database.getReference("Orders");
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendMessage();
                    table_order.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Order order = new Order(cartOrder, tempName, totalBD.toString(), finalTotalBD.toString(), taxAmountBD.toString());
                        table_order.child(newInvoice).setValue(order);
                        Intent create = new Intent(FinalCheckoutScreen.this, InvoiceScreen.class);
                        create.putExtra("invoice", newInvoice);
                        startActivity(create);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }


        });


    }
    private void sendMessage() {
        EditText emailT = findViewById(R.id.email);
        final String email = emailT.getText().toString();
        if (emailT.getText().toString().equals("")) {
            Toast.makeText(FinalCheckoutScreen.this, "Please enter an email address", Toast.LENGTH_LONG).show();
        }
        else {
            final ProgressDialog dialog = new ProgressDialog(FinalCheckoutScreen.this);
            dialog.setTitle("Sending Email");
            dialog.setMessage("Please wait");
            dialog.show();
            final String message = "=======================" + "\n" + tempName + ",\n\nPlease review the details below.\n\n" +
                    " \nTotal Before Tax: $" + totalBD.toString() + "\nTax Amount: $" + taxAmountBD.toString() + "\n=======================" + "\n\nTotal: $" + finalTotalBD.toString()
                    + "\n\nYour order number is: #MMH-" + newInvoice + ".\n\nYour order will be ready for pickup in 8 minutes.\n\nThank you for choosing the Paragon Cafe.\n\n";
            Thread sender = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        GMailSender sender = new GMailSender("noReplyMMHParagon@gmail.com", "Targhee1");
                        sender.sendMail("Your paragon order details",
                                message,
                                "noReplyMMHParagon@gmail.com",
                                email);
                        dialog.dismiss();
                    } catch (Exception e) {
                        Log.e("mylog", "Error: " + e.getMessage());
                    }
                }
            });
            sender.start();
        }
    }
}
