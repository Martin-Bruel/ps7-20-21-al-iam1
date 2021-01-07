package com.example.polyville2.activity;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.polyville2.R;
import com.example.polyville2.model.Currency;
import com.example.polyville2.model.CurrencyType;

import java.util.List;

public class CurrencyAdapter extends BaseAdapter {

    private List<Currency> currencies;
    private Context context;
    public  CurrencyAdapter(List<Currency> c, Context context){
        currencies=c;
        this.context=context;
    }
    @Override
    public int getCount() {
        return currencies.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.currency_balance, viewGroup, false);
        }
        TextView currencyTV = view.findViewById(R.id.currency_tv);
        currencyTV.setText(currencies.get(i).getType());

        TextView priceTv = view.findViewById(R.id.currency_textView);
        priceTv.setText(currencies.get(i).getBalance()+"");
        return view;
    }
}