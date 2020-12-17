package fr;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.client.RESTClient;
import fr.model.Traffic;
import io.cucumber.java8.En;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrafficStepdef implements En {

    RESTClient client;
    String path;
    String reponse;
    ObjectMapper mapper = new ObjectMapper();
    List<Traffic> traffics;

    String store;
    Integer nb;
    String date;

    public TrafficStepdef(){

        //Backgroud
        Given("Un Client REST", () -> client = new RESTClient());



        //Determiner le magasin avec le plus d'influence
        When("J'envoie une requete GET sur {string}", (String path) -> reponse = client.sendRequest(path));
        And("Je parse les donnees", () -> traffics = Arrays.asList(mapper.readValue(reponse, Traffic[].class)));
        And("Je parcours les traffics pour trouver le magasin avec le plus d'influence", () -> storeWithHigherTraffic(traffics));
        Then("Le magasin avec le plus d'influence est {string} avec {int} personnes", (String store, Integer nb) -> {
            assertEquals(store, this.store);
            assertEquals(nb, this.nb);
        });

        //Determiner la date avec le plus d'influence
        And("Je parcours les traffics pour trouver la date avec le plus d'influence", () -> dateWithHigherTraffic(traffics));
        Then("La date avec le plus d'influence est aujourd'hui - {int} avec {int} personnes", (Integer minusDay, Integer nb) -> {
            assertEquals(LocalDate.now().minusDays(minusDay).toString(), this.date);
            assertEquals(nb, this.nb);
        });

        //Determiner le magasin qui à le plus d'influence aujourd'hui
        When("J'envoie une requete GET sur {string} + date d'aujourd'hui", (String path) -> reponse = client.sendRequest(path + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getDayOfMonth()));


        //Determiner la date avec le plus d'influence pour un magasin
        When("J'envoie une requete GET sur {string} + {int}", (String path, Integer storeId) -> reponse = client.sendRequest(path + storeId));


        //Realiser une requete innexistante
        When("Je creer une requete GET sur {string}", (String path) -> this.path = path);
        Then("L'envoie creer une erreure", () -> assertThrows(IOException.class, ()->client.sendRequest(path)));
    }

    public void storeWithHigherTraffic(List<Traffic> traffics){

        Map<String, Integer> map = new HashMap<>();
        traffics.forEach((traffic) ->{
            String store = traffic.getStore();
            if(map.containsKey(store)) map.compute(store, (key, val) -> val+traffic.getNb());
            else map.put(store, traffic.getNb());
        });
        Map.Entry<String, Integer> e = map.entrySet().stream().max(Map.Entry.comparingByValue()).get();
        nb = e.getValue();
        store = e.getKey();
    }

    public void dateWithHigherTraffic(List<Traffic> traffics){

        Map<String, Integer> map = new HashMap<>();
        traffics.forEach((traffic) ->{
            String date = traffic.getDate();
            if(map.containsKey(date)) map.compute(date, (key, val) -> val+traffic.getNb());
            else map.put(date, traffic.getNb());
        });
        Map.Entry<String, Integer> e = map.entrySet().stream().max(Map.Entry.comparingByValue()).get();
        nb = e.getValue();
        date = e.getKey();
    }

}
