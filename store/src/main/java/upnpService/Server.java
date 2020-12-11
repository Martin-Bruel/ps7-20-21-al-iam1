package upnpService;

import model.Store;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;
import dataBase.JsonReader;

public class Server {


    public static final Store store = JsonReader.read();
    final private String SERVER_NAME = "ServerStore"+store.getName();
    final private String SCHEMA_NAME = "server";
    final private String MANUFACTURE_NAME = "PS7-AL-IAM1";

    /**
     * Start server for the store
     * @throws ValidationException if can't create services
     */
    public void startServer() throws ValidationException {

        //Creation d'un service UPNP
        final UpnpService upnpService = new UpnpServiceImpl();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                upnpService.shutdown();
            }
        });
        upnpService.getRegistry().addDevice(createDevice());
    }


    /**
     * Create device server which can be use on upnp network
     * @return device server
     * @throws ValidationException if can't create services
     * @throws LocalServiceBindingException if bad name on services method
     */
    LocalDevice createDevice() throws ValidationException, LocalServiceBindingException {

        DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier(SERVER_NAME));
        DeviceType type =new UDADeviceType(SCHEMA_NAME, 1);

        DeviceDetails details =
                new DeviceDetails(
                        SERVER_NAME,
                        new ManufacturerDetails(MANUFACTURE_NAME),
                        new ModelDetails(
                                SCHEMA_NAME,
                                "A store with details, service and subscribe.",
                                "v1"
                        )
                );



        LocalService<StoreManager> storeService = new AnnotationLocalServiceBinder().read(StoreManager.class);

        storeService.setManager(new DefaultServiceManager(storeService, StoreManager.class));

        return new LocalDevice(identity, type, details, storeService);

    /* Several services can be bound to the same device:
    return new LocalDevice(
            identity, type, details, icon,
            new LocalService[] {switchPowerService, myOtherService}
    );
    */

    }
}
