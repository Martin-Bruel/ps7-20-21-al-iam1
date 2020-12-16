package weather;

import org.junit.jupiter.api.Test;

import exceptions.IncorrectUnitWeatherAPIException;
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
    void callOpenWeatherAPI() throws IOException, IncorrectUnitWeatherAPIException {
        OpenWeatherAPI api = new OpenWeatherAPI("metric");
        ArrayList<Label> labels = api.callApi(anyDouble(), anyDouble());
        assertTrue(labels.get(0) != null);
        assertTrue(labels.get(1) != null);
    }

    @Test
    void callWeatherBit() throws IOException, IncorrectUnitWeatherAPIException {
        WeatherBitAPI api = new WeatherBitAPI("M");
        ArrayList<Label> labels = api.callApi(anyDouble(), anyDouble());
        assertTrue(labels.get(0) != null);
        assertTrue(labels.get(1) != null);
    }

    @Test
    void setTemp() throws IncorrectValuesTemperature {
        TempLabel templabel = TempLabel.CHILLY;
        double[] range1 = {10, 15};
        templabel.setRange(range1);
        assertTrue(templabel.range == range1);
    }

    @Test
    void throwError(){
        assertThrows(IncorrectUnitWeatherAPIException.class, () -> new OpenWeatherAPI("meter"));
        assertThrows(IncorrectUnitWeatherAPIException.class, () -> new WeatherBitAPI("meter"));
    }

    
}