package com.example.polyville2.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

public class BluetoothPayment extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    public BluetoothPayment(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        mmDevice = device;

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            String uuidString = "d0c722b07e1511e1b0c40800200c9a66";
            UUID uuid = new UUID(
                    new BigInteger(uuidString.substring(0, 16), 16).longValue(),
                    new BigInteger(uuidString.substring(16), 16).longValue());
            System.out.println(uuid.toString());
            tmp = device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            System.out.println("Socket's create() method failed. Stack trace:" + e);
        }
        System.out.println("ligne 35");
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.

        bluetoothAdapter.cancelDiscovery();
        System.out.println("ligne 42");
        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            System.out.println("ligne 46");
            mmSocket.connect();
            System.out.println("ligne 48");
        } catch (IOException connectException) {
            System.out.println("ligne 51");
            // Unable to connect; close the socket and return.
            try {
                System.out.println("close ligne 54");
                mmSocket.close();
            } catch (IOException closeException) {
                System.out.println("Could not close the client socket. Stack trace: "+closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        // manageMyConnectedSocket(mmSocket);
        System.out.println("connected");
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close the client socket. Stack trace: "+e);
        }
    }

}
