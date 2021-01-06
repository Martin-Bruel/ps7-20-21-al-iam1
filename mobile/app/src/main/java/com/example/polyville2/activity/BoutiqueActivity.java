package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.polyville2.R;
import com.example.polyville2.model.Currency;
import com.example.polyville2.model.Store;

import java.io.Serializable;
import java.util.List;

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

        if (s.getLocalCurrencies().size()>0){
            String acceptedCurrencies= "This store accepts ";
            List<Currency> localCurrencies=s.getLocalCurrencies();
            for (int i =0;i<localCurrencies.size();i++){
                if (i==localCurrencies.size()-1)
                    acceptedCurrencies += "and "+localCurrencies.get(i).toString()+".";
                else
                    acceptedCurrencies += localCurrencies.get(i).toString()+", ";
            }
            TextView tv_currencies = findViewById(R.id.tv_currencies);
            tv_currencies.setText(acceptedCurrencies);
        }



        Button bt1 = findViewById(R.id.buttonPublications);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ListPublicationActivity.class);
                intent.putExtra("publications", (Serializable) s.getAllPublications());
                startActivity(intent);
            }
        });

        Button bt2 = findViewById(R.id.buttonProduits);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ListProduitActivity.class);
                intent.putExtra("products", (Serializable) s.getProducts());
                startActivity(intent);
            }
        });

    }
}