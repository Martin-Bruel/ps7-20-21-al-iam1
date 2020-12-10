package upnpService;

import org.fourthline.cling.model.ValidationException;

public class Main {

    public static void main(String[] args) {

        try {
            new Server().startServer();
        } catch (ValidationException e) {
            System.out.println("Server can't start...");
            e.printStackTrace();
        }
    }
}
