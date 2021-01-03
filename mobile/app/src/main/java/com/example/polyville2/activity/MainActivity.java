package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.polyville2.model.Publication;
import com.example.polyville2.model.Store;
import com.example.polyville2.service.BluetoothScanner;
import com.example.polyville2.service.BluetoothService;
import com.example.polyville2.upnp.CallActionDevice;
import com.example.polyville2.upnp.DiscoveryDevice;
import com.example.polyville2.R;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements Observer {
    private ListView boutiques;
    private List<RemoteDevice> devicesList = new ArrayList<>();
    private Map<String,RemoteDevice> macOfDevice = new HashMap<>();
    private Map<RemoteDevice,Store> linkIOTAndStore= new HashMap<>();
    private List<Store> storeList = new ArrayList<>();
    private DiscoveryDevice registryListener ;
    private AndroidUpnpService upnpService;
    private BaseAdapter boutiquesAdapter;
    private ServiceConnection serviceConnection;
    private Context context=this;
    private PublicaitonNotification publicaitonNotification;
    private BluetoothScanner scanner;

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


        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);

        scanner = new BluetoothScanner();
        scanner.getBluetoothManager().addObserver(this);

        registerReceiver(scanner, filter);

        Intent intent = new Intent(this, BluetoothService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }

        publicaitonNotification = new PublicaitonNotification(context);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable.equals(registryListener)) {
            RemoteDevice device = (RemoteDevice) o;
            devicesList.add(device);
            CallActionDevice callActionDevice = new CallActionDevice((RemoteDevice)o, upnpService);
            callActionDevice.getStoredevice(context);
            callActionDevice.getMACOfDevice(context);
        }
        else{
            String mac = (String) o;
            if(macOfDevice.containsKey(mac)){
                RemoteDevice d = macOfDevice.get(mac);
                Store s = linkIOTAndStore.get(macOfDevice.get(mac));
                CallActionDevice callActionDevice = new CallActionDevice(d, upnpService);
                callActionDevice.getPublicationsNotifiableOfDevice(context, s);
                scanner.getBluetoothManager().knownDevices.add(mac);
                System.out.println(mac + " is known.");
            }
        }
    }

    public void addStore(Store s,RemoteDevice remoteDevice){
        System.out.println("------------ici store--------------- ");
        storeList.add(s);
        linkIOTAndStore.put(remoteDevice,s);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                boutiquesAdapter.notifyDataSetChanged();
            }
        });
    }

    public void removeStore(RemoteDevice remoteDevice){
        storeList.remove(linkIOTAndStore.get(remoteDevice));
        linkIOTAndStore.remove(remoteDevice);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                boutiquesAdapter.notifyDataSetChanged();
            }
        });
    }
    public void linkMacAndDevice(String s, RemoteDevice device){
        macOfDevice.put(s,device);
        scanner.getBluetoothManager().discover(s);
    }

    public void verifyBluetoothActivation(BluetoothAdapter adapter){
        if(adapter != null && !adapter.isEnabled()){
                Toast.makeText(getApplicationContext(), "You need to enabled bluetooth", Toast.LENGTH_SHORT).show();
                Intent enabledIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enabledIntent, 11);
            }
    }

    public void notifyPublication(Publication pub){
        publicaitonNotification.sendNotificaiton(pub.getTitle(), pub.getDescription());
    }
}