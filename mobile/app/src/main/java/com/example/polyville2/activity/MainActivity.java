package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.polyville2.model.Store;
import com.example.polyville2.upnp.CallActionDevice;
import com.example.polyville2.upnp.DiscoveryDevice;
import com.example.polyville2.R;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer {
    private ListView boutiques;
    private List<RemoteDevice> devicesList = new ArrayList<>();
    private List<Store> storeList = new ArrayList<>();
    private DiscoveryDevice registryListener ;
    private AndroidUpnpService upnpService;
    private BaseAdapter boutiquesAdapter;
    private ServiceConnection serviceConnection;


    public AndroidUpnpService getUpnpService() {
        return upnpService;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boutiques = findViewById(R.id.list_boutiques);
        boutiquesAdapter = new BoutiqueAdapter(devicesList,storeList,this);

        boutiques.setAdapter(boutiquesAdapter);
        registryListener = new DiscoveryDevice();
        registryListener.addObserver(this);

        serviceConnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName className, IBinder service) {
                upnpService = (AndroidUpnpService) service;

                // Get ready for future device advertisements
                upnpService.getRegistry().addListener(registryListener);

                // Search asynchronously for all devices, they will respond soon
                upnpService.getControlPoint().search();
            }

            public void onServiceDisconnected(ComponentName className) {
                upnpService = null;
            }
        };

        getApplicationContext().bindService(
                new Intent(this, AndroidUpnpServiceImpl.class),
                serviceConnection,
                Context.BIND_AUTO_CREATE
        );

    }

    @Override
    public void update(Observable observable, Object o) {
        CallActionDevice callActionDevice = new CallActionDevice((RemoteDevice) o,upnpService);
        callActionDevice.getStoredevice(this);

    }

    public void addStore(Store s,RemoteDevice device){
        devicesList.add(device);
        storeList.add(s);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                boutiquesAdapter.notifyDataSetChanged();
            }
        });

    }
}