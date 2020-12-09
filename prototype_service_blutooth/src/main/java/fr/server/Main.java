package fr.server;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        LocalDevice local = null;

        StreamConnectionNotifier notifier;
        StreamConnection connection = null;

        // setup the server to listen for connection
        try {
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            UUID uuid = new UUID("d0c722b07e1511e1b0c40800200c9a66", false);
            System.out.println("UUID : " + uuid.toString());
            System.out.println("Adresse : " + local.getBluetoothAddress());


            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier) Connector.open(url);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // waiting for connection
        while(true) {
            try {
                System.out.println("waiting for connection...");
                notifier.acceptAndOpen();
                System.out.println("After AcceptAndOpen...");

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
