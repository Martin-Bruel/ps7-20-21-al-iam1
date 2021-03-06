package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polyville2.R;
import com.example.polyville2.api.PolyvilleAPI;
import com.example.polyville2.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ConnectionActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        BottomNavigationItemView buttonOne = findViewById(R.id.action_home);
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.userToken),"");
        String password = sharedPref.getString(getString(R.string.password),"");
        System.out.println("token1"+token);
        if(!token.equals("")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                Account account = mapper.readValue(token, Account.class);
                callApi(account.getUsername(), password);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("connect to server");
        }
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        TextView createAccount = findViewById(R.id.tv_connection_create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), AccountCreationActivity.class);
                startActivity(activity2Intent);
            }
        });

        EditText edit_text_mail = findViewById(R.id.editText_login_mail);
        EditText editText_password = findViewById(R.id.editText_login_password);
        Button validate = findViewById(R.id.button_login_validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = edit_text_mail.getText().toString();
                String password = editText_password.getText().toString();
                if(mail.length()==0 || password.length()==0){
                    Toast.makeText(getApplicationContext(), R.string.accountCreationToastEmptyField, Toast.LENGTH_LONG).show();
                }else{
                    callApi(mail, password);
                }

            }
        });
    }

    public void callApi(String mail, String password){
        String url = PolyvilleAPI.url + "/connect?username="+mail+"&password="+password ;
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
                    connect(response, password);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        });
        t.start();
    }


    public void connect(String response, String password) {

        if (response.contains("null")) {
            errorApi();
        }else{
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.userToken),response);
            editor.putString(getString(R.string.password),password);

            editor.apply();
            System.out.println("connect to server");
            goToUserPage(response);
        }
    }

    public void errorApi(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), R.string.badIdentifiant, Toast.LENGTH_LONG).show();
            }});
    }

    public void goToUserPage(String response){
        Intent intent = new Intent(getApplicationContext(), CurrencyBalanceActivity.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Account account = mapper.readValue(response,Account.class);
            intent.putExtra("currencies", (Serializable) account.getCurrencies());
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        finish();
    }
}