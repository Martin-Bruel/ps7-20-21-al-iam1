package com.example.polyville2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.List;

public class BoutiqueAdapter extends BaseAdapter{
    List<RemoteDevice> devices;
    Context context;
    public BoutiqueAdapter(List<RemoteDevice> devices, Context context){
        this.devices=devices;
        this.context=context;
    }
    @Override
    public int getCount() {
        return 0;
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
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.boutique_item, parent, false);
        }
        TextView boutiqueTV = view.findViewById(R.id.boutique_tv);
        boutiqueTV.setText(devices.get(i).getDetails().getFriendlyName());
        return null;
    }
}
