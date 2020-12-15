package com.example.polyville2.upnp;

import android.content.Context;
import android.widget.BaseAdapter;


import com.example.polyville2.activity.MainActivity;
import com.example.polyville2.model.Product;
import com.example.polyville2.model.Store;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.Service;

import java.io.IOException;
import java.util.List;

public class CallActionDevice {
    public final int STOREPOS = 1;
    public final int MACPOS=0;
    public final int PRODUCTPOS=2;


    RemoteDevice device;
    AndroidUpnpService upnpservice;

    public CallActionDevice(RemoteDevice device,AndroidUpnpService upnpservice){
        this.device=device;
        this.upnpservice=upnpservice;
    }
    public void getStoredevice(final Context context){
        Service service = device.getServices()[0];
        Action statusAction = service.getActions()[STOREPOS];

        ActionInvocation invocation = new ActionInvocation(statusAction);
        ActionCallback callback= new ActionCallback(invocation) {
            @Override
            public void success(ActionInvocation invocation) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Store s = mapper.readValue((String)invocation.getOutput()[0].getValue(),Store.class);
                    getProductsOfDevice(context,s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {

            }
        };
        upnpservice.getControlPoint().execute(callback);
    }

    public void getProductsOfDevice(final Context context, final Store store){
        Service service = device.getServices()[0];
        Action statusAction = service.getActions()[PRODUCTPOS];

        ActionInvocation invocation = new ActionInvocation(statusAction);
        final ActionCallback callback= new ActionCallback(invocation) {
            @Override
            public void success(ActionInvocation invocation) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Product[] products = mapper.readValue((String)invocation.getOutput()[0].getValue(),Product[].class);
                    for(Product p:products) {
                        store.addProduct(p);
                    }
                    ((MainActivity)context).addStore(store);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {

            }
        };
        upnpservice.getControlPoint().execute(callback);
    }


    public void getMACOfDevice(final Context context){
        Service service = device.getServices()[0];
        Action statusAction = service.getActions()[MACPOS];

        ActionInvocation invocation = new ActionInvocation(statusAction);
        final ActionCallback callback= new ActionCallback(invocation) {
            @Override
            public void success(ActionInvocation invocation) {
                ((MainActivity)context).linkMacAndDevice((String)invocation.getOutput()[0].getValue(),device);
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {

            }
        };
        upnpservice.getControlPoint().execute(callback);
    }

}
