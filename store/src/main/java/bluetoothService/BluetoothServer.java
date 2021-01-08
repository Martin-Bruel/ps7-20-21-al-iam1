package bluetoothService;

import traffic.RESTClient;
import upnpService.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;


// d0c722b07e1511e1b0c40800200c9a66
public class BluetoothServer extends Thread {

	RESTClient restClient = new RESTClient();

	/** Constructor */
	public BluetoothServer() {
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
		
		// setup the server to listen for connection
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);

			UUID uuid = new UUID("d0c722b07e1511e1b0c40800200c9a66", false);
			System.out.println(uuid.toString());

			String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
			notifier = (StreamConnectionNotifier) Connector.open(url);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// waiting for connection
		while (true) {
			try {
				 System.out.println("waiting for connection...");
				 // Wait for client connection
				StreamConnection conn = notifier.acceptAndOpen();
				// New client connection accepted; get a handle on it
				RemoteDevice rd = RemoteDevice.getRemoteDevice(conn);
				System.out.println("New client connection... " + rd.getFriendlyName(false));
				// Read input message, in this example a String
				System.out.println("Creating socket...");
				DataInputStream dataIn = conn.openDataInputStream();
				System.out.println("Socket opened.");
				byte[] bytesReceived = new byte[1024];
				int nbByte = dataIn.read(bytesReceived);
				String s = new String(bytesReceived, 0, nbByte, StandardCharsets.UTF_8);
				System.out.println("Data receive : " + s );
				OutputStream dataOut = conn.openOutputStream();
				byte[] bytesToSend = "OK".getBytes();
				System.out.println("Sending data...");
				dataOut.write(bytesToSend); 
				System.out.println("Data send.");

				String[] data = s.split(",");
				updateAmountApi(data[1], data[0], data[2]);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

	public void updateAmountApi(String userId, String amount, String currency){

		String json = "{\n" +
				"  \"accountId\": "+ userId + ",\n" +
				"  \"amount\": " + amount + ",\n" +
				"  \"currencyType\": \""+currency+"\",\n" +
				"  \"date\": \"" + LocalDate.now().toString() + "\",\n" +
				"  \"storeId\": " + Server.store.getId() + "\n" +
				"}";

		try {
			restClient.sendRequest("POST", json, "/transaction/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



