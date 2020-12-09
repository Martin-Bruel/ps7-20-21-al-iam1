package fr.client;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread th = new Thread(new DiscoverDevice());
        th.start();
        th.join();
    }
}
