package upnpService;

import model.Store;
import org.fourthline.cling.model.ValidationException;
import restService.JsonReader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ValidationException {


        Store store = JsonReader.read();
        Server server = new Server(store);
        server.startServer();


    }
}
