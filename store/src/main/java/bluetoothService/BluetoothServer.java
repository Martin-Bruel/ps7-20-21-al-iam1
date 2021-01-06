package bluetoothService;

import java.io.DataInputStream;
import java.util.UUID;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class BluetoothServer extends Thread {
    public String name = "StoreBTServer";
    LocalDevice local = null;
    StreamConnectionNotifier server = null;
    StreamConnection conn = null;

    public BluetoothServer() throws BluetoothStateException {
        System.out.println("Setting device to be discoverable...");
        javax.bluetooth.LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
    }

    @Override
	public void run() {
		waitForConnection();
	}

	/** Waiting for connection from devices */
	private void waitForConnection() {
		// retrieve the local Bluetooth device object
		LocalDevice local = null;

		StreamConnectionNotifier notifier;
		StreamConnection connection = null;

		// setup the server to listen for connection
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);

			javax.bluetooth.UUID uuid = new javax.bluetooth.UUID("d0c722b07e1511e1b0c40800200c9a66", false);
			System.out.println(uuid.toString());

            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier)Connector.open(url);
		} catch (Exception e) {
            e.printStackTrace();
            return;
        }

		// waiting for connection
		while(true) {
			try {
				System.out.println("waiting for connection...");
	            connection = notifier.acceptAndOpen();
	            System.out.println("\n\n\nAfter AcceptAndOpen...\n\n\n");

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
