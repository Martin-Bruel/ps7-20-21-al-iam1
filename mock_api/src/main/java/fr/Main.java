package fr;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.client.RESTClient;
import fr.model.Traffic;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        RESTClient client = new RESTClient();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Mock API Dominique");
        System.out.println();
        System.out.println("Cette application permet de montrer le bon fonctionnement de l'API de statistique d'influence.");
        System.out.println("Les statistiques suivantes sont calculé en focntion des données de l'API.");
        System.out.println();

        //connexion avec l'API
        System.out.println("Connexion sur l'API : " + client.URL);
        try {
            if(client.sendRequest("/").equals("It's working !")) System.out.println("OK.");
        } catch (IOException e) {
            error(1);
            return;
        }

        //magasin avec le plus d'influence
        try {
            List<Traffic> traffics = new ArrayList<>(Arrays.asList(mapper.readValue(client.sendRequest("/traffic/"), Traffic[].class)));
            shopWithHigherTraffic(traffics);
        } catch (IOException e) {
            error(2);
        }


        //Le jour avec le plus d'afluence
        try {
            List<Traffic> traffics = new ArrayList<>(Arrays.asList(mapper.readValue(client.sendRequest("/traffic/"), Traffic[].class)));
            dateWithHigherTraffic(traffics);
        } catch (IOException e) {
            error(2);
        }

        //Le jour avec le plus d'afluence
        try {
            List<Traffic> traffics = new ArrayList<>(Arrays.asList(mapper.readValue(client.sendRequest("/traffic/"), Traffic[].class)));
            dateWithShorterTraffic(traffics);
        } catch (IOException e) {
            error(2);
        }

        //moyenne d'influence par magasin
        //TODO moyenne d'influence

    }

    public static void shopWithHigherTraffic(List<Traffic> traffics){

        Map<String, Integer> map = new HashMap<>();
        traffics.forEach((traffic) ->{
            String store = traffic.getStore();
            if(map.containsKey(store)) map.compute(store, (key, val) -> val+traffic.getNb());
            else map.put(store, traffic.getNb());
        });
        System.out.println();
        System.out.println("### MAGASIN AVEC PLUS D'INFLUENCE  ###");
        Map.Entry<String, Integer> e = map.entrySet().stream().max(Map.Entry.comparingByValue()).get();
        System.out.println(e.getKey() + " : " + e.getValue());
    }

    public static void dateWithHigherTraffic(List<Traffic> traffics){

        Map<String, Integer> map = new HashMap<>();
        traffics.forEach((traffic) ->{
            String date = traffic.getDate();
            if(map.containsKey(date)) map.compute(date, (key, val) -> val+traffic.getNb());
            else map.put(date, traffic.getNb());
        });
        System.out.println();
        System.out.println("### DATE AVEC PLUS D'INFLUENCE  ###");
        Map.Entry<String, Integer> e = map.entrySet().stream().max(Map.Entry.comparingByValue()).get();
        System.out.println(e.getKey() + " : " + e.getValue());
    }

    public static void dateWithShorterTraffic(List<Traffic> traffics){

        Map<String, Integer> map = new HashMap<>();
        traffics.forEach((traffic) ->{
            String date = traffic.getDate();
            if(map.containsKey(date)) map.compute(date, (key, val) -> val+traffic.getNb());
            else map.put(date, traffic.getNb());
        });
        System.out.println();
        System.out.println("### DATE AVEC LE MOINS D'INFLUENCE  ###");
        Map.Entry<String, Integer> e = map.entrySet().stream().min(Map.Entry.comparingByValue()).get();
        System.out.println(e.getKey() + " : " + e.getValue());
    }



    public static void error(int code){

        System.out.println("Erreur...           code d'erreur : " + code);
        System.out.println();
        System.out.println("Solutions : ");

        switch (code){

            case 1:
                System.out.println(" - redemarrer l'API");
                System.out.println(" - changer l'URL de l'API");
            case 2:
                System.out.println(" - telecharger la derniere version");

        }
    }
}
