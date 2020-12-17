package com.example.polyville2.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.example.polyville2.activity.MainActivity;
import com.example.polyville2.upnp.CallActionDevice;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class BluetoothSearcher extends Observable {

    private Context context;
    private BluetoothAdapter adapter ;
    private List<RemoteDevice> currentMACBlDevice;
    public BluetoothSearcher(Context c){
        this.context=c;
        adapter= BluetoothAdapter.getDefaultAdapter();
        currentMACBlDevice = new ArrayList<>();
    }
    public BluetoothAdapter getAdapter(){
        return adapter;
    }

    public void bluetoothResearch(final Map<String,RemoteDevice> macOfDevice, final AndroidUpnpService upnpService){

        final BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(device.getAddress()!=null ){
                        //System.out.println("ici"+device.getName());
                        String bluetoothMAC = device.getAddress();
                        bluetoothMAC=bluetoothMAC.replace(":","");
                        RemoteDevice remoteDevice = macOfDevice.get(bluetoothMAC);
                        if (remoteDevice!=null) {
                            System.out.println("ici device discover with bl");
                           // CallActionDevice callActionDevice = new CallActionDevice(remoteDevice, upnpService);
                           // callActionDevice.getStoredevice(context);
                            if(!currentMACBlDevice.contains(remoteDevice)) {
                                currentMACBlDevice.add(remoteDevice);
                            }
                        }
                    }
                }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                    System.out.println("ici discoverFinish");
                    List<RemoteDevice> copie = new ArrayList<>();
                    copie.addAll(currentMACBlDevice);
                    setChanged();
                    notifyObservers(copie);
                    adapter.startDiscovery();
                    currentMACBlDevice =new ArrayList<>();
                }

            }
        };
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        context.registerReceiver(receiver, filter);
        adapter.startDiscovery();
    }

}
