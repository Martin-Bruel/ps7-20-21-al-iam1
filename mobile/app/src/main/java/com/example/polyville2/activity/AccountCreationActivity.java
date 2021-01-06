package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.polyville2.R;
import com.example.polyville2.api.PolyvilleAPI;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccountCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        EditText edit_text_mail = findViewById(R.id.editText_account_creation_mail);
        EditText edit_text_password = findViewById(R.id.editText_account_creation_password);
        EditText edit_text_password_confirmation = findViewById(R.id.editText_account_creation_confirm_password);
        Button button_validate = findViewById(R.id.button_creation_account_validate);

        button_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = edit_text_mail.getText().toString();
                String password = edit_text_password.getText().toString();
                System.out.println("mail:" + (mail.length() > 0) + "\t pasword/ " + (password.length() > 0) + "\t" + (edit_text_password_confirmation.getText().toString().length() > 0));
                if (mail.length() == 0 || password.length() == 0 || edit_text_password_confirmation.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.accountCreationToastEmptyField, Toast.LENGTH_LONG).show();
                } else if (!isMail(mail)) {
                    Toast.makeText(getApplicationContext(), R.string.accountCreationToastMailMalformed, Toast.LENGTH_LONG).show();
                } else if (!password.equals(edit_text_password_confirmation.getText().toString())) {
                    Toast.makeText(getApplicationContext(), R.string.accountCreationToastDifferentPassword, Toast.LENGTH_LONG).show();

                } else {

                    String url = PolyvilleAPI.url + "/create?username="+mail+"&password="+password  ;
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String response = " ";
                            StringBuilder result = new StringBuilder();
                            try {

                               //System.out.println("result1"+url);
                                URL myUrl = new URL(url);
                                HttpURLConnection con;
                                con = (HttpURLConnection) myUrl.openConnection();
                                con.setAllowUserInteraction(true);
                                con.setDoOutput(true);
                                con.setDoInput(true);
                                con.setUseCaches(false);
                                con.setRequestMethod("POST");
                                con.setRequestProperty("Content-Type", "application/json; utf-8");
                                String jsonInputString = "{ \"username\":\"" + mail + "\", \"password\":\"" + password + "\" }";

                                OutputStreamWriter wr = new OutputStreamWriter ( con.getOutputStream());
                                wr.write( jsonInputString );
                                wr.flush();
                                wr.close();

                                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                String line;
                                while ((line = in.readLine()) != null) {
                                    response+=line;
                                }
                                System.out.println("result"+response);
                                resultCreation(response);
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }

                    });
                    t.start();
                }
            }
        });

    }

    private boolean isMail(String mail) {
        Pattern mailPattern = Pattern.compile("^([a-z0-9._%-]+)@([a-z0-9.-_]+)\\.[a-z]{2,6}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = mailPattern.matcher(mail);
        return matcher.find();
    }

    private void resultCreation(String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (response.contains("false")) {
                    Toast.makeText(getApplicationContext(), R.string.existant_account, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.account_created, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}