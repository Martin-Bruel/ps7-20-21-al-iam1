package weather;

import java.io.IOException;
import java.util.ArrayList;

import exceptions.UndefinedWeatherAPIException;
import weather.apis.*;
import weather.model.WeatherAPIEnum;
import model.Label;

public class WeatherFacade {

    /**
     * 
     * public method that returns label according to the weather and temperature of
     * the given position units is metric or imperial to change units of temperature
     * (°F or °C) TODO : refactor units with builder pattern
     * 
     * @param api
     * @param latitude
     * @param longitude
     * @param units
     * @return
     * @throws IOException
     * @throws UndefinedWeatherAPIException
     */
    public static ArrayList<Label> getWeatherLabel(WeatherAPIEnum apiEnum, double latitude, double longitude,
            String units) throws IOException, UndefinedWeatherAPIException {
        ArrayList<Label> res = new ArrayList<>();
        WeatherAPI api = apiEnum.getApi();
        res = api.callApi(latitude, longitude, units);
        return res;
    }

}
