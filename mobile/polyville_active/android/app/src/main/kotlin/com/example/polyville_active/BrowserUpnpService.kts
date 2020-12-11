public class BrowserUpnpService: AndroidUpnpServiceImpl {


    override protected UpnpServiceConfiguration createConfiguration() {
        return new AndroidUpnpServiceConfiguration() {

            override public int getRegistryMaintenanceIntervalMillis() {
                return 7000;
            }


            override public ServiceType[] getExclusiveServiceTypes() {
                return new ServiceType[]{
                    new UDAServiceType("SwitchPower")
                };
            }
        };
    }
}