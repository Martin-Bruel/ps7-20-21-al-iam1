package com.example.polyville2.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ListView;

import com.example.polyville2.R;
import com.example.polyville2.model.Currency;
import com.example.polyville2.model.CurrencyType;

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
    }
}