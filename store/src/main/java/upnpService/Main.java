package upnpService;

import org.fourthline.cling.model.ValidationException;

import javax.bluetooth.BluetoothStateException;

public class Main {

    public static void main(String[] args) {

        try {
            new Server().startServer();
        } catch (ValidationException | BluetoothStateException e) {
            System.out.println("Server can't start...");
            e.printStackTrace();
        }
    }
}
