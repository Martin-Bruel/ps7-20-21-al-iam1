package com.example.polyville2.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothScanner extends BroadcastReceiver {

    private int cpt = 0;
    private String BLTAG = "BluetoothService";

    private final BluetoothManager bluetoothManager;

    public BluetoothScanner() {
        this.bluetoothManager = new BluetoothManager();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
            Log.d(BLTAG,"START SCAN : n" + ++cpt);
        }
        if (action.equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String mac = device.getAddress().replace(":","");
            Log.d(BLTAG, "FOUND : " + mac);
            bluetoothManager.addDevice(mac);
        }
        if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
            Log.d(BLTAG,"STOP SCAN : n" + cpt);
        }
    }

    public BluetoothManager getBluetoothManager() {
        return bluetoothManager;
    }
}
