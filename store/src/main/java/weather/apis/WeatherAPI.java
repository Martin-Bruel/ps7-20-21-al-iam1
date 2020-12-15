package weather.apis;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import model.Label;


public abstract class WeatherAPI {

    public abstract ArrayList<Label> callApi(double latitude, double longitude, String units) throws IOException;
    protected abstract ArrayList<Label> abstractGlobalWeather(JSONObject response);
    protected abstract String getWeather(JSONObject response);
    protected abstract double getTemp(JSONObject response);
    protected abstract Label abstractWeather(String weather);
    protected abstract Label abstractTemp(double temp);

}
