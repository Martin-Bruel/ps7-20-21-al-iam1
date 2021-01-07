package com.example.polyville2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ListView;

import com.example.polyville2.R;
import com.example.polyville2.model.Currency;
import com.example.polyville2.model.CurrencyType;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CurrencyBalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_balance);
        List<Currency> c = (List<Currency>) getIntent().getSerializableExtra("currencies");
        ListView lv = findViewById(R.id.list_view_currency);
        CurrencyAdapter c2 = new CurrencyAdapter(c,this);
        lv.setAdapter(c2);

        BottomNavigationItemView buttonOne = findViewById(R.id.action_home);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton buttonCredit = findViewById(R.id.activity_currency_balance_floatingActionButton);
        buttonCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreditAccountActivity.class));
                finish();
            }
        });
    }

}