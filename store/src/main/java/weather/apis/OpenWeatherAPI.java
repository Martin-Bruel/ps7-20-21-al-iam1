package weather.apis;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;

import model.Label;


public class OpenWeatherAPI extends WeatherAPI {
    String key;


    public OpenWeatherAPI(String key) {
        this.key = key;
    }

    @Override
    public ArrayList<Label> callApi(double latitude, double longitude, String units) throws IOException {
        ArrayList<Label> res = new ArrayList<>();
        String urlStr = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units="+ units +"&appid=" + this.key;
        URL url = new URL(urlStr);
        JSONTokener tokener = new JSONTokener(url.openStream()); 
        JSONObject response = new JSONObject(tokener);
        res = abstractGlobalWeather(response);
        return res;
    }


    @Override
    protected int getWeather(JSONObject response) {
        return response.getJSONArray("weather").getJSONObject(0).getInt("id"); // see https://openweathermap.org/current#parameter
    }


    @Override
    protected double getTemp(JSONObject response){
        return response.getJSONObject("main").getDouble("temp");
    }


}
