package com.example.paragonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

public class FinalCheckoutScreen extends AppCompatActivity {

    String name, total, tempName;
    TextView totalView;
    BigDecimal totalBD, taxAmountBD, finalTotalBD;
    Button placeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_checkout_screen);

        placeOrder = findViewById(R.id.finalBtn);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            total = extras.getString("total");
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
                " \nTotal Before Tax: $" + totalBD.toString()+"\nTax Amount: $"+ taxAmountBD.toString() + "\n======================="+ "\n\nTotal: $"+ finalTotalBD.toString());


    }
}