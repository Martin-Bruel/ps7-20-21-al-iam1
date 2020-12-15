package fr.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.model.Traffic;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RESTClientTest {

    RESTClient client = new RESTClient();
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void allTrafficTest() throws IOException {

        String response = client.sendRequest("/traffic/");
        List<Traffic> traffics = new ArrayList<>(Arrays.asList(mapper.readValue(response, Traffic[].class)));
        assertTrue(traffics.size() >= 9);
    }

    @Test
    void badRequestTest() {
        assertThrows(IOException.class, ()->client.sendRequest("/trafficheho/"));
    }

    @Test
    void trafficForStore() throws IOException {
        String response = client.sendRequest("/traffic/store/1");
        List<Traffic> traffics = new ArrayList<>(Arrays.asList(mapper.readValue(response, Traffic[].class)));
        assertTrue(traffics.size() >= 3);
    }

    @Test
    void trafficForDate()  {
        assertDoesNotThrow(()->client.sendRequest("/traffic/date/2020/1/1"));
    }

}