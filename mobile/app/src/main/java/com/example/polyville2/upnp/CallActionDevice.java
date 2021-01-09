package com.example.polyville2.upnp;

import android.content.Context;
import android.widget.BaseAdapter;


import com.example.polyville2.activity.MainActivity;
import com.example.polyville2.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * classe qui permet les appels UPNP
 */
public class CallActionDevice {
    public final int STOREPOS = 2;
    public final int MACPOS=0;
    public final int PRODUCTPOS=3;
    public final int PUBLICATIONPOS=4;
    public final int CONTEXTPUBPOS=1;

    RemoteDevice device;
    AndroidUpnpService upnpservice;

    public CallActionDevice(RemoteDevice device,AndroidUpnpService upnpservice){
        this.device=device;
        this.upnpservice=upnpservice;
    }

    /**
     * Appelle la méthode en UPNP qui rajoute tous les details au store
     * @param context
     */
    public void getStoredevice(final Context context){
        Service service = device.getServices()[0];
        Action statusAction = service.getActions()[STOREPOS];

        ActionInvocation invocation = new ActionInvocation(statusAction);
        ActionCallback callback= new ActionCallback(invocation) {
            @Override
            public void success(ActionInvocation invocation) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                try {
                    // récupère le store
                    Store s = mapper.readValue((String)invocation.getOutput()[0].getValue(),Store.class);
                    // récupère les produits du store
                    getProductsOfDevice(context,s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {

            }
        };
        upnpservice.getControlPoint().execute(callback);
    }

    /**
     * Récupère les produits du store
     * @param context
     * @param store
     */
    public void getProductsOfDevice(final Context context, final Store store){
        Service service = device.getServices()[0];
        Action statusAction = service.getActions()[PRODUCTPOS];

        ActionInvocation invocation = new ActionInvocation(statusAction);
        final ActionCallback callback= new ActionCallback(invocation) {
            @Override
            public void success(ActionInvocation invocation) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Product[] products = mapper.readValue((String)invocation.getOutput()[0].getValue(),Product[].class);
                    for(Product p:products) {
                        store.addProduct(p);
                    }
                    getPublicationsOfDevice(context,store);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {

            }
        };
        upnpservice.getControlPoint().execute(callback);
    }

    /**
     * Récupère les publications du store
     * @param context
     * @param store
     */
    public void getPublicationsOfDevice(final Context context, final Store store){
        Service service = device.getServices()[0];
        Action statusAction = service.getActions()[PUBLICATIONPOS];

        ActionInvocation invocation = new ActionInvocation(statusAction);
        final ActionCallback callback= new ActionCallback(invocation) {
            @Override
            public void success(ActionInvocation invocation) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Publication[] publications = mapper.readValue((String)invocation.getOutput()[0].getValue(),Publication[].class);
                    for(Publication p:publications) {
                        store.addPublication(p);
                    }
                    ((MainActivity)context).addStore(store,device);
                    // on récupère l'adresse MAC d'un store dès qu'on le détecte
                    getMACOfDevice(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {

            }
        };
        upnpservice.getControlPoint().execute(callback);
    }

    /**
     * L'adresse MAC du device visible en UPNP
     * @param context
     */
    public void getMACOfDevice(final Context context){
        Service service = device.getServices()[0];
        Action statusAction = service.getActions()[MACPOS];

        ActionInvocation invocation = new ActionInvocation(statusAction);
        final ActionCallback callback= new ActionCallback(invocation) {
            @Override
            public void success(ActionInvocation invocation) {
                ((MainActivity)context).linkMacAndDevice((String)invocation.getOutput()[0].getValue(),device);
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {

            }
        };
        upnpservice.getControlPoint().execute(callback);
    }

    /**
     * A chaque fois que je récupère une context publication je crée une notification
     * @param context
     * @param store
     */
    public void getContextPublicationsOfDevice(final Context context, final Store store){
        Service service = device.getServices()[0];
        Action statusAction = service.getActions()[CONTEXTPUBPOS];

        ActionInvocation invocation = new ActionInvocation(statusAction);
        final ActionCallback callback= new ActionCallback(invocation) {
            @Override
            public void success(ActionInvocation invocation) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Publication[] publications = mapper.readValue((String)invocation.getOutput()[0].getValue(),Publication[].class);
                    // notifie pour chaque publication
                    for(Publication p:publications) {
                        ((MainActivity)context).notifyPublication(p);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {

            }
        };
        upnpservice.getControlPoint().execute(callback);
    }

}
