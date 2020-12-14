package traffic;

import org.fourthline.cling.model.profile.RemoteClientInfo;
import upnpService.Server;

import java.io.IOException;
import java.net.*;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrafficManager {

    private final List<String> clients = new ArrayList<>();
    private Clock clock;
    private final LocalDate date;
    private final RESTClient restClient;

    public TrafficManager(Clock clock, RESTClient restClient){
        this.clock = clock;
        date = LocalDate.now(clock);
        this.restClient = restClient;
    }

    public TrafficManager(){
        this(Clock.systemUTC(), new RESTClient());
    }

    /**
     * Ajoute un nouveau client si celui-ci n'est pas deja present dans les clients
     * Cette methode traite l'adresse mac du client qui nous assure qu'il s'agit d'un client unique
     * Cette methode permet de déterminer l'adresse mac du client a partir de son adresse IP
     * @param client le client à ajouter
     */
    public void addNewClient() {

        try {
            updateServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*if (client != null) {

            try {
                InetAddress localIP = InetAddress.getByName(client.getConnection().getRemoteAddress().getHostAddress());
                NetworkInterface ni = NetworkInterface.getByInetAddress(localIP);
                byte[] hardwareAddress = ni.getHardwareAddress();
                if (hardwareAddress != null) {
                    String[] hexadecimalFormat = new String[hardwareAddress.length];
                    for (int i = 0; i < hardwareAddress.length; i++) {
                        hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
                    }
                    String macAdress = String.join("-", hexadecimalFormat);
                    System.out.println("Un client se connecte : " + String.join("-", hexadecimalFormat));
                    addNewClient(macAdress);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * Cette methode ajoute l'adresse mac du client si celle-ci n'est pas deja presente
     * @param macAdress adresse unique du client
     * @throws IOException si ne peut pas mettre à jour le serveur distant
     */
    boolean addNewClient(String macAdress) throws IOException {

        if(date.isBefore(LocalDate.now(clock))) clients.clear();
        if (!clients.contains(macAdress)) {
            clients.add(macAdress);
            return updateServer();
        }
        return false;
    }



    /**
     * Met à jour l'API de statistique
     * @throws IOException si la requete http ne peut pas etre créée
     */
    private boolean updateServer() throws IOException {

        String json = "{\n" +
                "    \"storeId\": "+Server.store.getId()+",\n" +
                "    \"nb\": 1,\n" +
                "    \"date\": \""+date+"\",\n" +
                "    \"store\": \""+Server.store.getName()+"\"\n" +
                "}";

        return restClient.sendRequest("POST", json);
    }


}
