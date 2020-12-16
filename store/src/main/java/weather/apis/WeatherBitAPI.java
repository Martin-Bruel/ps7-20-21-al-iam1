package weather.apis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;

import exceptions.IncorrectUnitWeatherAPIException;
import model.Label;
import weather.urlbuilder.StringUrl;
import weather.urlbuilder.StringUrlBuilder;


public class WeatherBitAPI extends WeatherAPI {
    String key;
    StringUrl stringURL;
    String units;

    public WeatherBitAPI(String units) throws IncorrectUnitWeatherAPIException {
        this.key = "50b12870871b4b03b6d77bea032c508c";
        this.stringURL = new StringUrlBuilder()
            .withRoot("https://api.weatherbit.io/v2.0/current?")
            .withLat("lat=")
            .withLon("&lon=")
            .withUnits("&units=")
            .withKey("&key=")
            .build();

        if(units != "" && units != "M" && units != "S" && units != "I"){
            throw new IncorrectUnitWeatherAPIException(
                    "Error units format for WeatherBit API. Units should be one of these : \"M\", \"S\", \"I\". If none is precised, default M (temperature in Celcius) is used.");
        }
        this.units = units;
    }

    @Override
    public ArrayList<Label> callApi(double latitude, double longitude){
        try {
            if(units != "" && units != "M" && units != "S" && units != "I"){
                throw new IncorrectUnitWeatherAPIException(
                        "Error units format for WeatherBit API. Units should be one of these : \"M\", \"S\", \"I\". If none is precised, default M (temperature in Celcius) is used.");
            }
        } catch (IncorrectUnitWeatherAPIException e) {
            e.printStackTrace();
        }
        ArrayList<Label> res = new ArrayList<>();
        try {
            URL url = buildURL(latitude, longitude, this.units);
            JSONTokener tokener = new JSONTokener(url.openStream()); 
            JSONObject response = new JSONObject(tokener);
            res = abstractGlobalWeather(response);
        return res;
        } catch (IOException e){
            System.err.println("An error occured while makin API call : ");
            e.printStackTrace();
            return null;
        }
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
