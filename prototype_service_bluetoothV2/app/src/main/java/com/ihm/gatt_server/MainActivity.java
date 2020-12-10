package com.ihm.gatt_server;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    BluetoothManager bluetoothManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGATTServer();
    }

    void startGATTServer(){

        bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothGattServer server = bluetoothManager.openGattServer(this, new GattServerCallback());
        if (server == null) {
            Log.w(TAG, "Unable to create GATT server");
            return;
        }

    }
}