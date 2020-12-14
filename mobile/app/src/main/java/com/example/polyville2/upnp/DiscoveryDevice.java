package com.example.polyville2.upnp;

import android.widget.BaseAdapter;
import android.widget.Toast;

import org.fourthline.cling.binding.xml.Descriptor;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DiscoveryDevice extends Observable implements RegistryListener {

    @Override
    public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
        System.out.println("service discover");
    }

    @Override
    public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device, Exception ex) {

    }

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        System.out.println("service found"+device.getDetails().getModelDetails().getModelName() );
        if(device.getDetails().getModelDetails().getModelName().equals("server")){
            //System.out.println("I'm here "+device.getDetails().getModelDetails().getModelName() );
            System.out.println(countObservers());
            setChanged();
            notifyObservers(device);
        }
    }

    @Override
    public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {

    }

    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {

    }

    @Override
    public void localDeviceAdded(Registry registry, LocalDevice device) {

    }

    @Override
    public void localDeviceRemoved(Registry registry, LocalDevice device) {

    }

    @Override
    public void beforeShutdown(Registry registry) {

    }

    @Override
    public void afterShutdown() {

    }
}
