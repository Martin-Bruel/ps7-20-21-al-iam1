package weather.apis;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import model.Label;

// TODO : add methods and study WeatherBit API documentation
public class WeatherBitAPI extends WeatherAPI {
    String key;

    public WeatherBitAPI(String key) {
        this.key = key;
    }

    @Override
    public ArrayList<Label> callApi(double latitude, double longitude, String units) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ArrayList<Label> abstractGlobalWeather(JSONObject response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getWeather(JSONObject response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected double getTemp(JSONObject response) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected Label abstractWeather(String weather) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Label abstractTemp(double temp) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
