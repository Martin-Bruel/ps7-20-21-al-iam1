package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.polyville2.R;
import com.example.polyville2.model.Store;

public class BoutiqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_boutique);
        Store s = (Store) getIntent().getSerializableExtra("store");

        //TextView tvAddress = findViewById(R.id.tv_address);
        //tvAddress.setText(s.getAddress());
        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(s.getName());


        ListView days = findViewById(R.id.lv_days); // ne s'affiche que si la liste n'est pas vide
        DayAdapter Days = new DayAdapter(s.getOpeningHours(), this);
        days.setAdapter(Days);


        Button bt1 = findViewById(R.id.buttonPublications);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO afficher les publications
            }
        });

        Button bt2 = findViewById(R.id.buttonProduits);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO afficher les produits
            }
        });

    }
}