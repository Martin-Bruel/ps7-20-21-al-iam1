package weather;

import org.junit.jupiter.api.Test;

import exceptions.IncorrectValuesTemperature;
import model.Label;
import weather.utils.TempLabel;
import weather.apis.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;

class WeatherFacadeTest {

    @Test
    void callOpenWeatherAPI() throws IOException {
        OpenWeatherAPI api = new OpenWeatherAPI();
        ArrayList<Label> labels = api.callApi(anyDouble(), anyDouble(), "metric");
        assertTrue(labels.get(0) != null);
        assertTrue(labels.get(1) != null);
    }

    @Test
    void callWeatherBit() throws IOException {
        WeatherBitAPI api = new WeatherBitAPI();
        ArrayList<Label> labels = api.callApi(anyDouble(), anyDouble());
        assertTrue(labels.get(0) != null);
        assertTrue(labels.get(1) != null);
    }

    @Test
    void setTemp() throws IncorrectValuesTemperature {
        TempLabel templabel = TempLabel.CHILLY;
        double[] range = {30, 21};
        assertThrows(IncorrectValuesTemperature.class, () -> templabel.setRange(range));
        double[] range2 = {10, 15};
        templabel.setRange(range2);
        assertTrue(templabel.range == range2);
    }

    
}