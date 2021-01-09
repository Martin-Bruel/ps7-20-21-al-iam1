package com.example.polyville2.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

public class BluetoothPayment extends Thread {
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private final String macAddress;
    public static int inProgress = 0;
    private String data;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    public BluetoothPayment(String macAddress, String data) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        this.macAddress = macAddress;
        this.data = data;
        // mmDevice = device;
        // mmSocket = tmp;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void run() {
        while(true) {
            // ---------- GET DEVICE FROM MAC ADDRESS ------------ //
            BluetoothDevice device = getDevice();
            mmDevice = device;

            // --------- SOCKET CREATION ------------------------ //
            try {
                mmSocket = socketCreation(mmDevice);
            } catch (IOException e) {
                System.out.println("Can't connect to socket...");
                e.printStackTrace();
            }
            // ------------ CONNECTION TO REMOTE DEVICE ---------------- //

            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter.cancelDiscovery();
            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                System.out.println("Connecting to bluetooth store...");
                mmSocket.connect();
                System.out.println("Connected to store bluetooth ! Sleep 5 sec while server connecting...");
                Thread.sleep(5000);
            } catch (IOException | InterruptedException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    System.out.println("Problem while connecting. Closing socket...");
                    mmSocket.close();
                    System.out.println("Socket closed.");
                } catch (IOException closeException) {
                    System.out.println("Could not close the client socket. Stack trace: " + closeException);
                }
                return;
            }
            sendData(data);
            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            // sendData("none");

        }
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close the client socket. Stack trace: "+e);
        }
    }

    private BluetoothDevice getDevice(){
        BluetoothDevice device = null;
        while(device == null){
            device = BluetoothManager.getDevice(this.macAddress);
            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return device;
    }

    private BluetoothSocket socketCreation(BluetoothDevice device) throws IOException {
        BluetoothSocket tmp = null;
        String uuidString = "d0c722b07e1511e1b0c40800200c9a66";
        UUID uuid = new UUID(
                new BigInteger(uuidString.substring(0, 16), 16).longValue(),
                new BigInteger(uuidString.substring(16), 16).longValue());
        System.out.println(uuid.toString());
        tmp = device.createRfcommSocketToServiceRecord(uuid);
        return tmp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int sendData(String data){

        System.out.println("Begin communication between store and phone");
        BluetoothPaymentProcess process = new BluetoothPaymentProcess(mmSocket);
        process.start();
        System.out.println("connected");
        process.write(data.getBytes());
        return 0;
    }

}
