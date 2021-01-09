package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.polyville2.model.Account;
import com.example.polyville2.model.Currency;
import com.example.polyville2.model.CurrencyType;
import com.example.polyville2.model.Publication;
import com.example.polyville2.model.Store;
import com.example.polyville2.service.BluetoothScanner;
import com.example.polyville2.service.BluetoothService;
import com.example.polyville2.upnp.CallActionDevice;
import com.example.polyville2.upnp.DiscoveryDevice;
import com.example.polyville2.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.meta.RemoteDevice;

import java.io.IOException;
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
    private Map<RemoteDevice,Store> linkIOTAndStore= new HashMap<>();
    private List<Store> storeList = new ArrayList<>();
    private DiscoveryDevice registryListener ;
    private AndroidUpnpService upnpService;
    private BaseAdapter boutiquesAdapter;
    private ServiceConnection serviceConnection;
    private Context context=this;
    private PublicaitonNotification publicaitonNotification;
    private BluetoothScanner scanner;
    SharedPreferences sharedPref;

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

        BottomNavigationItemView buttonOne = findViewById(R.id.action_account);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), ConnectionActivity.class);
                startActivity(activity2Intent);
            }
        });
    }

    public boolean isConnected(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.userToken),"");
        if(!token.equals("")){
            return true;
        }
        else return false;
    }



    /**
     * Dès qu'on découvre un appareil (store) en UPNP et en Bluetooth on rentre dans update
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        // if detection UPNP
        if(observable.equals(registryListener)) {
            RemoteDevice device = (RemoteDevice) o;
            // je le rajoute à ma liste
            devicesList.add(device);
            CallActionDevice callActionDevice = new CallActionDevice((RemoteDevice)o, upnpService);
            // je charge les détails du magasin
            callActionDevice.getStoredevice(context);
        }
        else{
            String mac = (String) o;
            if (macOfDevice.containsKey(mac)) {
                RemoteDevice d = macOfDevice.get(mac);
                Store s = linkIOTAndStore.get(macOfDevice.get(mac));
                CallActionDevice callActionDevice = new CallActionDevice(d, upnpService);
                callActionDevice.getContextPublicationsOfDevice(context, s);
                scanner.getBluetoothManager().knownDevices.add(mac);
                System.out.println(mac + " is known.");
                if (isConnected()) {
                    sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    String token = sharedPref.getString(getString(R.string.userToken), "");
                    if (!token.equals("")) {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            Account account = mapper.readValue(token, Account.class);
                            checkAndNotifyCommonCurrencies(s.getName(), s.getLocalCurrencies(), account.getCurrencies());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    /**
     * Ajoute le store bien récupéré au context
     * @param s
     * @param remoteDevice
     */
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

    /**
     * Rajouter à une hashmap le device et son adresse mac
     * @param macAddress
     * @param device
     */
    public void linkMacAndDevice(String macAddress, RemoteDevice device){
        macOfDevice.put(macAddress,device);
        scanner.getBluetoothManager().discover(macAddress);
        Store store = linkIOTAndStore.get(device);
        store.setMACaddress(macAddress);
    }

    public void verifyBluetoothActivation(BluetoothAdapter adapter){
        if(adapter != null && !adapter.isEnabled()){
                Toast.makeText(getApplicationContext(), "You need to enabled bluetooth", Toast.LENGTH_SHORT).show();
                Intent enabledIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enabledIntent, 11);
            }
    }

    /**
     * Envoie une notification avec titre et description
     * @param pub
     */
    public void notifyPublication(Publication pub){
        publicaitonNotification.sendNotificaiton(pub.getTitle(), pub.getDescription());
    }

    public void checkAndNotifyCommonCurrencies(String storeName, List<CurrencyType> storeCurrencies, List<Currency> accountCurrencies){
        List<CurrencyType> commonCurrencies = new ArrayList<>();
        for (CurrencyType c:storeCurrencies){
            for(Currency c2:accountCurrencies){
                if (c.toString().equals(c2.getType())){
                    commonCurrencies.add(c);
                }
            }
        }
        if(commonCurrencies.size()>0){
            String description = "This store accept your local currency.\n";
            for (int i =0;i<commonCurrencies.size();i++){
                System.out.println(commonCurrencies.get(i));
                if (i==commonCurrencies.size()-1){
                    if(i!=0)description += "and ";
                    description += commonCurrencies.get(i).toString()+".";
                }
                else
                    description += commonCurrencies.get(i).toString()+", ";
            }
            publicaitonNotification.sendNotificaiton(storeName, description);
        }

    }
}