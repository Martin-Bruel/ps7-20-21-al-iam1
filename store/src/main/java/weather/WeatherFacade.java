package weather;

import java.io.IOException;
import java.util.ArrayList;

import weather.model.WeatherAPI;
import weather.apis.*;
import model.Label;

public class WeatherFacade {

    /**
     * 
     * public method that returns label according to the weather and temperature of the given position
     * units is metric or imperial to change units of temperature (°F or °C)
     * TODO : refactor units with builder pattern
     * 
     * @param api
     * @param latitude
     * @param longitude
     * @param units
     * @return
     * @throws IOException
     */
    public static ArrayList<Label> getWeatherLabel(WeatherAPI api, double latitude, double longitude, String units) throws IOException {
        ArrayList<Label> res = new ArrayList<>();
        res = callAPIs(api, latitude, longitude, units);
        return res;
    }

    /**
     * 
     * private method called by getWeatherLabel to get label from the given API
     * TODO : add WeatherBit API
     * TODO : refactoring with interface pattern
     * 
     * @param api
     * @param latitude
     * @param longitude
     * @param units
     * @return
     * @throws IOException
     */
    private static ArrayList<Label> callAPIs(WeatherAPI api, double latitude, double longitude, String units) throws IOException {
        ArrayList<Label> res = new ArrayList<>();
        switch (api) {
            case OPENWEATHER:
                res = OpenWeatherAPI.callOpenWeatherAPI(latitude, longitude, units);
        default:
            break;
        }
        return res;
    }

}
