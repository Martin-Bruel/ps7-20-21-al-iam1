package weather.apis;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;

import model.Label;

// TODO : add methods and study WeatherBit API documentation
public class WeatherBitAPI extends WeatherAPI {
    String key;

    public WeatherBitAPI(String key) {
        this.key = key;
    }

    @Override
    public ArrayList<Label> callApi(double latitude, double longitude, String units) throws IOException {
        ArrayList<Label> res = new ArrayList<>();
        String urlStr = "https://api.weatherbit.io/v2.0/current?lat=" + latitude + "&lon=" + longitude + "&key=" + this.key;
        URL url = new URL(urlStr);
        JSONTokener tokener = new JSONTokener(url.openStream()); 
        JSONObject response = new JSONObject(tokener);
        res = abstractGlobalWeather(response);
        return res;
    }

    @Override
    protected int getWeather(JSONObject response) {
        return response.getJSONArray("data").getJSONObject(0).getJSONObject("weather").getInt("code"); // see https://www.weatherbit.io/api/swaggerui/weather-api-v2#/
    }

    @Override
    protected double getTemp(JSONObject response) {
        return response.getJSONArray("data").getJSONObject(0).getDouble("temp"); // see https://www.weatherbit.io/api/swaggerui/weather-api-v2#/
    }

    
}
