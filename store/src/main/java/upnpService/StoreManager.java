package upnpService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.fourthline.cling.binding.annotations.*;
import dataBase.JsonReader;

@UpnpService(
        serviceId = @UpnpServiceId("StoreManager"),
        serviceType = @UpnpServiceType(value = "StoreManager", version = 1)
)
public class StoreManager {

    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private String storeDetails = Server.store.detailsToJSON();

    public StoreManager() throws JsonProcessingException {
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "storeDetails"))
    public String getStoreDetails(){
        return storeDetails;
    }
}