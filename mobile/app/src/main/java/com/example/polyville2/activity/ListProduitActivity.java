package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.polyville2.R;
import com.example.polyville2.model.Product;
import com.example.polyville2.model.Store;

import java.util.List;

public class ListProduitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);
        List<Product> p = (List<Product>) getIntent().getSerializableExtra("products");
        ListView lv = findViewById(R.id.list_view_produit);
        ProduitAdapter p2 = new ProduitAdapter(p,this);
        lv.setAdapter(p2);

    }
}