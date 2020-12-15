package weather;

import org.junit.jupiter.api.Test;

import exceptions.IncorrectValuesTemperature;
import exceptions.UndefinedWeatherAPIException;
import model.Label;
import model.TempLabel;
import weather.model.WeatherAPIEnum;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;

class WeatherFacadeTest {

    @Test
    void callOpenWeatherAPI() throws IOException, UndefinedWeatherAPIException {
        ArrayList<Label> labels = WeatherFacade.getWeatherLabel(WeatherAPIEnum.OPENWEATHER, anyDouble(), anyDouble(), "metric");
        assertTrue(labels.get(0) != null);
        assertTrue(labels.get(1) != null);
    }

    @Test
    void callWeatherBit() throws IOException, UndefinedWeatherAPIException{
        ArrayList<Label> labels = WeatherFacade.getWeatherLabel(WeatherAPIEnum.WEATHERBIT, anyDouble(), anyDouble(), "S");
        assertTrue(labels.get(0) != null);
        assertTrue(labels.get(1) != null);
    }

    @Test
    void setTemp(){
        TempLabel templabel = TempLabel.CHILLY;
        double[] range = {30, 21};
        assertThrows(IncorrectValuesTemperature.class, () -> templabel.setRange(range));
    }

    
}