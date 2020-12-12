package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.polyville2.R;
import com.example.polyville2.model.Product;
import com.example.polyville2.model.Store;

public class BoutiqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);
        Store s = (Store) getIntent().getSerializableExtra("store");
        ListView lv = findViewById(R.id.list_view_produit);
        ProduitAdapter p = new ProduitAdapter(s,this);
        lv.setAdapter(p);

    }
}