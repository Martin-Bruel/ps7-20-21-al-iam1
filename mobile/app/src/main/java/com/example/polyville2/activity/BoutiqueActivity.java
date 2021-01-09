package com.example.polyville2.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polyville2.R;
import com.example.polyville2.model.Account;
import com.example.polyville2.model.CurrencyType;
import com.example.polyville2.model.Store;
import com.example.polyville2.service.BluetoothManager;
import com.example.polyville2.service.BluetoothPayment;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoutiqueActivity extends AppCompatActivity {

    private Account account;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_boutique);
        store = (Store) getIntent().getSerializableExtra("store");

        //TextView tvAddress = findViewById(R.id.tv_address);
        //tvAddress.setText(s.getAddress());
        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(store.getName());

        ListView days = findViewById(R.id.lv_days); // ne s'affiche que si la liste n'est pas vide
        DayAdapter Days = new DayAdapter(store.getOpeningHours(), this);
        days.setAdapter(Days);

        if (store.getLocalCurrencies().size()>0){
            String acceptedCurrencies= "This store accepts ";
            List<CurrencyType> localCurrencies=store.getLocalCurrencies();
            for (int i =0;i<localCurrencies.size();i++){
                if (i==localCurrencies.size()-1){
                    if(i!=0)acceptedCurrencies += "and ";
                    acceptedCurrencies += localCurrencies.get(i).toString()+".";
                }
                else
                    acceptedCurrencies += localCurrencies.get(i).toString()+", ";
            }
            TextView tv_currencies = findViewById(R.id.tv_currencies);
            tv_currencies.setText(acceptedCurrencies);
        }

        Spinner spinner = findViewById(R.id.spinner2);



        List<String> coins= new ArrayList<>();
        for (CurrencyType c: store.getLocalCurrencies()) {
            coins.add(c.name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,coins);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



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
                double priceDouble = Double.parseDouble(price);
                String currency = spinner.getSelectedItem().toString();
                if(!account.canPay(priceDouble, currency)) {
                    Toast.makeText(getApplicationContext(), "Not enough money...", Toast.LENGTH_LONG).show();
                    return;
                }
                // try to connect with shop
                Log.d("BLUETOOTH CONNECTION", "Beginnning to connect...");
                BluetoothPayment payment = new BluetoothPayment(store.getMACaddress(), price + "," + account.getId() + "," + currency);
                payment.start();
                System.out.println("Price : " + price);
                priceEditor.getText().clear();
                Toast.makeText(getApplicationContext(), "Transaction is send to the store.", Toast.LENGTH_LONG).show();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getRootView().getWindowToken(), 0);
            }
        });

        setUserId();

    }

    private void setUserId(){


        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.userToken),"");
        System.out.println("token1"+token);
        if(!token.equals("")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                account = mapper.readValue(token, Account.class);
                Button bt3 = findViewById(R.id.buttonPay);
                bt3.setEnabled(true);

                EditText priceEditor = findViewById(R.id.priceDecimalInput);
                priceEditor.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("connect to server");
        }
    }
}