package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.polyville2.R;
import com.example.polyville2.api.PolyvilleAPI;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                System.out.println("mail:"+(edit_text_mail.getText().toString().length() > 0) +"\t pasword/ "+(edit_text_password.getText().toString().length() > 0)+"\t"+(edit_text_password_confirmation.getText().toString().length()> 0));
                if(edit_text_mail.getText().toString().length()==0 || edit_text_password.getText().toString().length()==0 || edit_text_password_confirmation.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),R.string.accountCreationToastEmptyField,Toast.LENGTH_LONG).show();
                }else if(!isMail(edit_text_mail.getText().toString())){
                    Toast.makeText(getApplicationContext(),R.string.accountCreationToastMailMalformed,Toast.LENGTH_LONG).show();
                }else if(!edit_text_password.getText().toString().equals(edit_text_password_confirmation.getText().toString())) {
                    Toast.makeText(getApplicationContext(),R.string.accountCreationToastDifferentPassword,Toast.LENGTH_LONG).show();

                }else {
                    try {
                        URL myUrl = new URL(PolyvilleAPI.url+"/create/"+edit_text_mail.getText().toString()+"/"+edit_text_password.getText().toString());
                        HttpURLConnection con;
                        con = (HttpURLConnection) myUrl.openConnection();
                        con.setRequestMethod("GET");
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                          String line;
                          StringBuilder result = new StringBuilder();
                           while ((line=in.readLine()) != null) {
                                result.append(line);
                            }
                        System.out.println("account"+result);
                           if(result.equals("true")){
                               startActivity(new Intent(getApplicationContext(),ConnectionActivity.class));
                           }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private boolean isMail(String mail) {
        Pattern mailPattern = Pattern.compile("^([a-z0-9._%-]+)@([a-z0-9.-_]+)\\.[a-z]{2,6}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = mailPattern.matcher(mail);
        return matcher.find();
    }
}