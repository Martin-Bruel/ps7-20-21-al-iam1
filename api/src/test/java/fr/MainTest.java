package fr;

import fr.model.traffic.Traffic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainTest {

    @Autowired
    private StoreApi storeApi;

    @Autowired
    private TrafficApi trafficApi;

    @Autowired
    private Api api;

    @Test
    public void contextLoads() {
        assertThat(storeApi).isNotNull();
        assertThat(trafficApi).isNotNull();
        assertThat(api).isNotNull();
    }


}