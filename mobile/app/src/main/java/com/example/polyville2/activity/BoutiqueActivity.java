package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        Button bt3 = findViewById(R.id.pay_btn);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CharSequence text = store.getMACaddress();
                // Toast.makeText(getApplicationContext(), "hello there", Toast.LENGTH_LONG).show();
                System.out.println("device connected : "+store.getMACaddress());
                BluetoothDevice device = BluetoothManager.getDevice(store.getMACaddress());
                BluetoothPayment payment = new BluetoothPayment(device);
                new Thread(payment).start();
            }
        });

    }
}