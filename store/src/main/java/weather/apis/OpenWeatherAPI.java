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

public class OpenWeatherAPI extends WeatherAPI {
    String key;
    StringUrl stringURL;

    public OpenWeatherAPI() {
        this.key = "b7a405fea8328d8c19c034d9a8b673aa";
        this.stringURL = new StringUrlBuilder().withRoot("https://api.openweathermap.org/data/2.5/weather?")
                                                .withLat("lat=")
                                                .withLon("&lon=")
                                                .withUnits("&units=")
                                                .withKey("&appid=")
                                                .build();
    }

    @Override
    public ArrayList<Label> callApi(double latitude, double longitude, String units){
        ArrayList<Label> res = new ArrayList<>();
        try{
            URL url = buildURL(latitude, longitude, units);
            JSONTokener tokener = new JSONTokener(url.openStream());
            JSONObject response = new JSONObject(tokener);
            res = abstractGlobalWeather(response);
            return res;
        } catch( IOException e){
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
        return response.getJSONArray("weather").getJSONObject(0).getInt("id"); // see
                                                                               // https://openweathermap.org/current#parameter
    }



    @Override
    protected double getTemp(JSONObject response){
        return response.getJSONObject("main").getDouble("temp");
    }




}
