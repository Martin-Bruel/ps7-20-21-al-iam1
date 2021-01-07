package com.example.polyville2.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.polyville2.R;
import com.example.polyville2.model.Store;
import com.example.polyville2.service.BluetoothManager;
import com.example.polyville2.service.BluetoothPayment;

import java.io.Serializable;

public class BoutiqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_boutique);
        Store store = (Store) getIntent().getSerializableExtra("store");

        // try to connect with shop
        Log.d("BLUETOOTH CONNECTION", "Beginnning to connect...");
        BluetoothPayment payment = new BluetoothPayment(store.getMACaddress());
        new Thread(payment).start();

        //TextView tvAddress = findViewById(R.id.tv_address);
        //tvAddress.setText(s.getAddress());
        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(store.getName());

        ListView days = findViewById(R.id.lv_days); // ne s'affiche que si la liste n'est pas vide
        DayAdapter Days = new DayAdapter(store.getOpeningHours(), this);
        days.setAdapter(Days);


        Button bt1 = findViewById(R.id.buttonPublications);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ListPublicationActivity.class);
                intent.putExtra("publications", (Serializable) store.getAllPublications());
                startActivity(intent);
            }
        });

        Button bt2 = findViewById(R.id.buttonProduits);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ListProduitActivity.class);
                intent.putExtra("products", (Serializable) store.getProducts());
                startActivity(intent);
            }
        });

        EditText priceEditor = findViewById(R.id.priceDecimalInput);
        Button bt3 = findViewById(R.id.buttonPay);
        bt3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String price = priceEditor.getText().toString();
                // String to double : double priceDouble = Double.parseDouble(price);
                //TODO : vérifier que le prix est inférieur à l'argent dans le porte-feuille
                // if(price > porte-feuille) WARNING : not enough money
                System.out.println("Price : " + price);
                payment.sendData("hello there PAy");
            }
        });

    }
}