package com.example.polyville2.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.polyville2.R;
import com.example.polyville2.model.Store;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.RemoteDevice;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class BoutiqueAdapter extends BaseAdapter{
    private List<Store> stores;
    private List<RemoteDevice> devices;
    private Context context;
    public BoutiqueAdapter(List<RemoteDevice> devices, List<Store>stores,Context context){
        this.devices=devices;
        this.context=context;
        this.stores=stores;
    }
    @Override
    public int getCount() {
        return devices.size();
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
    public View getView(final int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.boutique_item, parent, false);
        }
        TextView boutiqueTV = view.findViewById(R.id.boutique_tv);
        boutiqueTV.setText(stores.get(i).getName());
        ConstraintLayout cl = view.findViewById(R.id.item_constraint_layout);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,BoutiqueActivity.class);
                intent.putExtra("store", (Serializable) stores.get(i));
                context.startActivity(intent);
            }
        });
        return view;
    }
}
