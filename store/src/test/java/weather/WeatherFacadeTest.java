package weather;

import org.junit.jupiter.api.Test;

import exceptions.IncorrectValuesTemperature;
import model.Label;
import model.TempLabel;
import weather.model.WeatherAPI;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;

class WeatherFacadeTest {

    @Test
    void callWeather() throws IOException {
        ArrayList<Label> labels = WeatherFacade.getWeatherLabel(WeatherAPI.OPENWEATHER, anyDouble(), anyDouble(), "metric");
        assertTrue(labels.get(0) != null);
        assertTrue(labels.get(1) != null);
        TempLabel templabel = TempLabel.CHILLY;
        double[] range = {30, 21};
        assertThrows(IncorrectValuesTemperature.class, () -> templabel.setRange(range));
    }

}