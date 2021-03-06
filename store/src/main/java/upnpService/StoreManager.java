package upnpService;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.fourthline.cling.binding.annotations.*;
import traffic.TrafficManager;

@UpnpService(
        serviceId = @UpnpServiceId("StoreManager"),
        serviceType = @UpnpServiceType(value = "StoreManager", version = 1)
)
public class StoreManager {

    private TrafficManager trafficManager = new TrafficManager();

    @UpnpStateVariable(defaultValue = "0", sendEvents = true)
    private String storeDetails = Server.store.detailsToJSON();
    
    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private String storeProducts = Server.store.productsToJSON();
    
    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private String MAC = Server.store.getBluetoothMac();

    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private String storePublications = Server.store.allPublicationsToJSON();

    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private String storeContextPublications = Server.store.contextPublicationsToJSON();

    public StoreManager() throws JsonProcessingException {
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "storeDetails"))
    public String getStoreDetails(){
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

    @UpnpAction(out = @UpnpOutputArgument(name = "storePublications"))
    public String getStorePublications(){
        return storePublications;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "storeContextPublications"))
    public String getStoreContextPublications(){
        trafficManager.addNewClient();
        return storeContextPublications;
    }
}