package com.example.polyville2.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.polyville2.R;
import com.example.polyville2.model.Item;
import com.example.polyville2.model.Product;
import com.example.polyville2.model.Store;

import org.seamless.util.logging.SystemOutLoggingHandler;

import java.util.List;

public class ProduitAdapter extends BaseAdapter {
    private List<Product> products;
    private Context context;
    public  ProduitAdapter(List<Product> p, Context context){
        products=p;
        this.context=context;
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.produit_item, viewGroup, false);
        }
        TextView produitTV = view.findViewById(R.id.produit_tv);
        produitTV.setText(products.get(i).getName());

        TextView priceTv = view.findViewById(R.id.price_textView);
        priceTv.setText(((Item)products.get(i)).getPrice()+"€");
        return view;
    }
}
