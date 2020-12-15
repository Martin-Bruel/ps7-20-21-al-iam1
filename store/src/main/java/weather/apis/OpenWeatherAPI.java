package weather.apis;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;

import model.Label;
import model.TempLabel;


public class OpenWeatherAPI extends WeatherAPI {
    String key;


    public OpenWeatherAPI(String key) {
        this.key = key;
    }

    /**
     * 
     * make the call to OpenWeather API with given position and given units system.
     * then abstract the data to exploitable datas for other classes and return the
     * corresponding labels
     * 
     * @param latitude
     * @param longitude
     * @param units
     * @return ArrayList of labels
     * @throws IOException
     */
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


    /**
     * 
     * return labels according to weather/temperature retrieved from API call
     * 
     * @param response
     * @return
     */
    @Override
    protected ArrayList<Label> abstractGlobalWeather(JSONObject response) {
        ArrayList<Label> res = new ArrayList<>();
        String mainWeather = getWeather(response);
        double temp = getTemp(response);
        res.add(abstractWeather(mainWeather));
        res.add(abstractTemp(temp));
        return res;
    }

    /**
     * 
     * parse JSON response to retrieve weather
     * 
     * @param response
     * @return
     */
    @Override
    protected String getWeather(JSONObject response) {
        return response.getJSONArray("weather").getJSONObject(0).getString("main"); // see https://openweathermap.org/current#parameter
    }

    /**
     * 
     * parse JSON reponse to retrieve temperature
     * 
     * @param response
     * @return
     */
    @Override
    protected double getTemp(JSONObject response){
        return response.getJSONObject("main").getDouble("temp");
    }

    /**
     * 
     * return Label according to weather string retrieved from API call 
     * 
     * @param weather
     * @return
     */
    @Override
    protected Label abstractWeather(String weather){
        Label res;
        switch(weather){
            case "Clear": 
                res = Label.SUNNY;
                break;
            case "Clouds": 
                res = Label.CLOUDS;
                break;
            case "Rain": 
                res = Label.RAIN;
                break;
            case "Snow": 
                res = Label.SNOW;
                break;
            case "Extreme": 
                res = Label.STORM;
                break;
            default : 
                res = Label.UNDEFINED;
                break;
        }
        return res;
    }

    /**
     * 
     * return Label according to temperature retrieved from API call
     * 
     * @param temp
     * @return
     */
    @Override
    protected Label abstractTemp(double temp){
        Label res;
        TempLabel labeltemp = TempLabel.getLabel(temp);
        switch(labeltemp){
            case HOT:
                res = Label.HOT;
                break;
            case WARM:
                res = Label.WARM;
                break;
            case MILD:
                res = Label.MILD;
                break;
            case CHILLY:
                res = Label.CHILLY;
                break;
            case COLD:
                res = Label.COLD;
                break;
            case FREEZE:
                res = Label.FREEZE;
            default:
                res = Label.UNDEFINED;
				break;
        }
        return res;
    }

}
