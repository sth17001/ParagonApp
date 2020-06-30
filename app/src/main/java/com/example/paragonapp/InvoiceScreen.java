package com.example.paragonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InvoiceScreen extends AppCompatActivity {
    TextView invoice;
    String invoiceNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_screen);

        Button home = findViewById(R.id.home);
        invoice = findViewById(R.id.invoice);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            invoiceNum = "#MMH-"+extras.getString("invoice");
        }

        invoice.setText("Your order number is: " + invoiceNum+".\n\nYour order will be ready for pickup in 8 minutes.\n\nThank you for choosing the Paragon Cafe.");

        //sendEmail();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(InvoiceScreen.this, loggedIn.class);
                startActivity(create);
            }
        });
    }
    @Override
    public void onBackPressed() {


    }
}