package com.example.polyville2.service;

import android.util.Log;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class BluetoothManager extends Observable {

    public final Set<String> allDevices = new HashSet<>();
    public final Set<String> knownDevices = new HashSet<>();
    private String BLTAG = "BluetoothService";

    public void addDevice(String mac){
        if(allDevices.add(mac)){
            Log.d(BLTAG, mac + " detected.");
            setChanged();
            notifyObservers(mac);
        }
    }

    public void discover(String mac){
        if (allDevices.contains(mac) && !knownDevices.contains(mac)){
            Log.d(BLTAG, mac + " detected.");
            setChanged();
            notifyObservers(mac);
        }
    }
}
