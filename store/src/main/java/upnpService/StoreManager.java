package upnpService;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public StoreManager() throws JsonProcessingException {
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "storeDetails"))
    public String getStoreDetails(RemoteClientInfo client){

        trafficManager.addNewClient(client);
        return storeDetails;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "storeProducts"))
    public String getStoreProducts(){
        return storeProducts;
    }
}