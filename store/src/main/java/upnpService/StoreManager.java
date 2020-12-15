package upnpService;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;

import org.fourthline.cling.binding.annotations.*;
import org.fourthline.cling.model.profile.RemoteClientInfo;
import traffic.TrafficManager;

@UpnpService(
        serviceId = @UpnpServiceId("StoreManager"),
        serviceType = @UpnpServiceType(value = "StoreManager", version = 1)
)
public class StoreManager {

    private TrafficManager trafficManager = new TrafficManager();

    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private String storeDetails = Server.store.detailsToJSON();
    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private String storeProducts = Server.store.productsToJSON();
    
    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private String MAC = Server.store.getBluetoothMac();

    public StoreManager() throws JsonProcessingException {
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "storeDetails"))
    public String getStoreDetails(){

        trafficManager.addNewClient();
        return storeDetails;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "storeProducts"))
    public String getStoreProducts(){
        return storeProducts;
    }
    

    @UpnpAction(out = @UpnpOutputArgument(name = "MAC"))
    public String getMAC() {
    	return MAC;
    }
}