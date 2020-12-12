package com.example.polyville2.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.polyville2.R;
import com.example.polyville2.model.Store;

import org.seamless.util.logging.SystemOutLoggingHandler;

public class ProduitAdapter extends BaseAdapter {
    private Store store;
    private Context context;
    public  ProduitAdapter(Store s,Context context){
        this.store=s;
        this.context=context;
    }
    @Override
    public int getCount() {
        return store.getProducts().size();
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
        produitTV.setText(store.getProducts().get(i).getName());
        return view;
    }
}
