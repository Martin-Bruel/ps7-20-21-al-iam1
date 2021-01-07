package com.example.polyville2.service;

import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.polyville2.activity.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

public class BluetoothPaymentProcess extends Thread implements Handler.Callback {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private byte[] mmBuffer; // mmBuffer store for the stream
    private Handler handler;

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }

    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }

    public BluetoothPaymentProcess(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        Looper.prepare();
        handler = new Handler(this);

        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            System.out.println("Error occurred when creating input stream. Stack trace : "+e);
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error occurred when creating output stream. Stack trace : " + e);
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        mmBuffer = new byte[1024];
        int numBytes; // bytes returned from read()
//
//        // Keep listening to the InputStream until an exception occurs.
//        while (true) {
//            try {
//                // Read from the InputStream.
//                // numBytes = mmInStream.read(mmBuffer);
//                // Send the obtained bytes to the UI activity.
////                Message readMsg = handler.obtainMessage(
////                        MessageConstants.MESSAGE_READ, numBytes, -1,
////                        mmBuffer);
////                readMsg.sendToTarget();
//            } catch (IOException e) {
//                System.out.println("Input stream was disconnected. Stack trace : " + e);
//                break;
//            }
//        }
    }

    // Call this from the main activity to send data to the remote device.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void write(byte[] bytes) {
        mmBuffer = new byte[1024];
        int numBytes;
        System.out.println("sending data... Data : " + new String(bytes, StandardCharsets.UTF_8));
        try {
            mmOutStream.write(bytes);
            System.out.println("Receiving data...");
            numBytes = mmInStream.read(mmBuffer);
            System.out.println("receive : " + new String(mmBuffer, StandardCharsets.UTF_8));

//            // Share the sent message with the UI activity.
//            Message writtenMsg = handler.obtainMessage(
//                    MessageConstants.MESSAGE_WRITE, -1, -1, bytes);
//            writtenMsg.sendToTarget();
            // Thread.sleep(5000);
        } catch (IOException e) {
            System.out.println("Error occurred when sending data. Stack trace : " + e);

            // Send a failure message back to the activity.
            Message writeErrorMsg =
                    handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast",
                    "Couldn't send data to the other device");
            writeErrorMsg.setData(bundle);
            handler.sendMessage(writeErrorMsg);
        }
    }

    // Call this method from the main activity to shut down the connection.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close the connect socket. Stack trace : " + e);
        }
    }


}

