package com.example.polyville2.service;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class BluetoothManager extends Observable {

    private final Set<String> devices = new HashSet<>();

    public void addDevice(String mac){
        if(devices.add(mac)){
            setChanged();
            notifyObservers(mac);
        }
    }
}
