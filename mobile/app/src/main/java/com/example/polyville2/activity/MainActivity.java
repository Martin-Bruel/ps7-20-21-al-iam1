package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.polyville2.model.Store;
import com.example.polyville2.upnp.CallActionDevice;
import com.example.polyville2.upnp.DiscoveryDevice;
import com.example.polyville2.R;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer {
    private ListView boutiques;
    private List<RemoteDevice> devicesList = new ArrayList<>();
    private Map<String,RemoteDevice> macOfDevice = new HashMap<>();
    private List<Store> storeList = new ArrayList<>();
    private DiscoveryDevice registryListener ;
    private AndroidUpnpService upnpService;
    private BaseAdapter boutiquesAdapter;
    private ServiceConnection serviceConnection;
    private Context context=this;

    public AndroidUpnpService getUpnpService() {
        return upnpService;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boutiques = findViewById(R.id.list_boutiques);
        boutiquesAdapter = new BoutiqueAdapter(storeList,this);

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


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Not to annoy user.
                Toast.makeText(this, "Permission must be granted to use the app.", Toast.LENGTH_SHORT).show();
            } else {

                // Request permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }


    }

    @Override
    public void update(Observable observable, Object o) {
        RemoteDevice device = (RemoteDevice) o;
        devicesList.add(device);
        bluetoothResearch();
        CallActionDevice callActionDevice = new CallActionDevice((RemoteDevice) o,upnpService);
        callActionDevice.getMACOfDevice(this);

    }

    public void addStore(Store s){
        System.out.println("------------ici store--------------- ");
        storeList.add(s);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                boutiquesAdapter.notifyDataSetChanged();
            }
        });

    }
    public void fuckAndroid(){
        System.out.println("------------ici store--------------- ");
    }
    public void linkMacAndDevice(String s, RemoteDevice device){
        macOfDevice.put(s,device);
    }

    public void bluetoothResearch(){

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        final BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(device.getAddress()!=null ){
                        String bluetoothMAC = device.getAddress();
                        bluetoothMAC=bluetoothMAC.replace(":","");
                        RemoteDevice remoteDevice = macOfDevice.get(bluetoothMAC);
                        if (remoteDevice!=null) {
                            CallActionDevice callActionDevice = new CallActionDevice(remoteDevice, upnpService);
                            callActionDevice.getStoredevice(context);
                        }
                    }
                }

            }
        };
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(receiver, filter);
        verifyBluetoothActivation(adapter);
        adapter.startDiscovery();
    }
    public void verifyBluetoothActivation(BluetoothAdapter adapter){
        if(adapter != null && !adapter.isEnabled()){
                Toast.makeText(getApplicationContext(), "You need to enabled bluetooth", Toast.LENGTH_SHORT).show();
                Intent enabledIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enabledIntent, 11);
            }
    }
}