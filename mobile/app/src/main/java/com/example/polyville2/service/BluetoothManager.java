package com.example.polyville2.service;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class BluetoothManager extends Observable {

    public final Set<String> allDevices = new HashSet<>();
    public final Set<String> knownDevices = new HashSet<>();
    public static Set<BluetoothDevice> bluetoothDevices = new HashSet<>();
    private String BLTAG = "BluetoothService";

    public void addDevice(BluetoothDevice bluetoothDevice){
        String mac = bluetoothDevice.getAddress().replace(":","");
        if(allDevices.add(mac)){
            Log.d(BLTAG, mac + " detected.");
            setChanged();
            notifyObservers(mac);
        }
        bluetoothDevices.add(bluetoothDevice);
    }

    public void discover(String mac){
        if (allDevices.contains(mac) && !knownDevices.contains(mac)){
            Log.d(BLTAG, mac + " detected.");
            setChanged();
            notifyObservers(mac);
        }
    }

    public static BluetoothDevice getDevice(String macAddress){
        for(BluetoothDevice device : bluetoothDevices){
            String addressPlain = device.getAddress().replace(":",""); // getAddress return AA:BB:CC:DD but macAddress is AABBCCDD
            if(addressPlain.equals(macAddress)){
                return device;
            }
        }
        return null;
    }
}
