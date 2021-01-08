package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.polyville2.R;
import com.example.polyville2.api.PolyvilleAPI;
import com.example.polyville2.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CreditAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_account);

        EditText amountTv = findViewById(R.id.edit_text_credit_account);
        Spinner spinner = findViewById(R.id.spinner);
        List<String> coins= List.of("POLYCOIN","SICOIN", "BIOT MONEY") ;
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
        Button validate = findViewById(R.id.button);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = amountTv.getText().toString();//Integer.parseInt(amountTv.getText().toString());
                Toast.makeText(getApplicationContext(), "Your balance account is update.", Toast.LENGTH_LONG).show();
                callApi(amount,spinner.getSelectedItem().toString());
                finish();
            }
        });
    }


    public void callApi(String amount,String currencyType){

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.userToken),"");
        String password = sharedPref.getString(getString(R.string.password),"");
        ObjectMapper mapper = new ObjectMapper();
        try {
            Account a = mapper.readValue(token, Account.class);
            String url = PolyvilleAPI.url + "/balanceIncrement?username="+a.getUsername()+"&password="+password+ "&amount="+amount+"&currencyType="+currencyType ;

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String response = "";
                    try {

                        System.out.println("result1"+url);
                        URL myUrl = new URL(url);
                        HttpURLConnection con;
                        con = (HttpURLConnection) myUrl.openConnection();
                        con.setAllowUserInteraction(true);
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.setUseCaches(false);
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Content-Type", "application/json; utf-8");

                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null) {
                            response+=line;
                        }
                        //connect(response, password);
                        System.out.println(response+"jsdfgklbdjfglgjflkjfkjgkfgjlfjklfjgklfgjklfgjbklgjbklgjkblgjklbgjbklgjklbgjbjgkllbjklfgjdk");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

            });
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}