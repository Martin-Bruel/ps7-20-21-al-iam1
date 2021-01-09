package com.example.polyville2.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class BluetoothScanner extends BroadcastReceiver {

    private final BluetoothManager bluetoothManager;

    public BluetoothScanner() {
        this.bluetoothManager = new BluetoothManager();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            bluetoothManager.addDevice(device);
            System.out.println("----------------------------- bluetooth device added -------------------- ");
        }
    }

    public BluetoothManager getBluetoothManager() {
        return bluetoothManager;
    }


}
