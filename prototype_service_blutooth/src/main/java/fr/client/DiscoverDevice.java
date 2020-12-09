package fr.client;

import javax.bluetooth.*;
import java.io.IOException;

public class DiscoverDevice implements Runnable, DiscoveryListener {


    @Override
    public void run() {
        LocalDevice localDevice = null;
        try {
            localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            DiscoveryAgent agent = localDevice.getDiscoveryAgent();
            agent.startInquiry(DiscoveryAgent.GIAC, this);
            synchronized (this){
                wait();
            }

        } catch (BluetoothStateException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
        System.out.println(remoteDevice.getBluetoothAddress());
        try {
            System.out.println(remoteDevice.getFriendlyName(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void servicesDiscovered(int i, ServiceRecord[] serviceRecords) {

    }

    @Override
    public void serviceSearchCompleted(int i, int i1) {

    }

    @Override
    public void inquiryCompleted(int i) {
        synchronized (this){
            notifyAll();
        }
    }
}
