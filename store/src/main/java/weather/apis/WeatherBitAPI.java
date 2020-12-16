package weather.apis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;

import model.Label;
import weather.urlbuilder.StringUrl;
import weather.urlbuilder.StringUrlBuilder;


public class WeatherBitAPI extends WeatherAPI {
    String key;
    StringUrl stringURL;

    public WeatherBitAPI() {
        this.key = "50b12870871b4b03b6d77bea032c508c";
        this.stringURL = new StringUrlBuilder()
            .withRoot("https://api.weatherbit.io/v2.0/current?")
            .withLat("lat=")
            .withLon("&lon=")
            .withUnits("&units=")
            .withKey("&key=")
            .build();
    }

    @Override
    public ArrayList<Label> callApi(double latitude, double longitude, String units) throws IOException {
        ArrayList<Label> res = new ArrayList<>();
        URL url = buildURL(latitude, longitude, units);
        JSONTokener tokener = new JSONTokener(url.openStream()); 
        JSONObject response = new JSONObject(tokener);
        res = abstractGlobalWeather(response);
        return res;
    }

    @Override
    public URL buildURL(double latitude, double longitude, String units) throws MalformedURLException {
        String res = this.stringURL.getRoot() + this.stringURL.getLat() + latitude + this.stringURL.getLon() + longitude;
        if(units != "") res += this.stringURL.getUnits() + units;
        res += this.stringURL.getKey() + this.key;
        return new URL(res);
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
