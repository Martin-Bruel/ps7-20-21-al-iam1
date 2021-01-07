// package traffic;

// import org.junit.jupiter.api.Test;

// import java.io.IOException;
// import java.time.Clock;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;

// class TrafficManagerTest {

//     @Test
//     void addNewClient() throws IOException {

//         RESTClient restClientMock = mock(RESTClient.class);
//         when(restClientMock.sendRequest(anyString(), anyString())).thenReturn(true);

//         TrafficManager trafficManager = new TrafficManager(Clock.systemUTC(), restClientMock);
//         assertTrue(trafficManager.addNewClient("1"));
//     }

// }