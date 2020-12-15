package weather.apis;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import model.Label;
import model.TempLabel;


public abstract class WeatherAPI {

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
    public abstract ArrayList<Label> callApi(double latitude, double longitude, String units) throws IOException;
    
    /**
     * 
     * return labels according to weather/temperature retrieved from API call
     * 
     * @param response
     * @return
     */
    protected ArrayList<Label> abstractGlobalWeather(JSONObject response){
        ArrayList<Label> res = new ArrayList<>();
        int mainWeather = getWeather(response);
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
    protected abstract int getWeather(JSONObject response);

    /**
     * 
     * parse JSON reponse to retrieve temperature
     * 
     * @param response
     * @return
     */
    protected abstract double getTemp(JSONObject response);

    /**
     * 
     * return Label according to weather string retrieved from API call 
     * 
     * @param weather
     * @return
     */
    protected Label abstractWeather(int weatherCode){
        if( weatherCode >= 200 && weatherCode < 300) return Label.STORM;
        if( weatherCode >= 300 && weatherCode < 600) return Label.RAIN;
        if( weatherCode >= 600 && weatherCode < 700) return Label.SNOW;
        if( weatherCode >= 700 && weatherCode < 800) return Label.MIST;
        if( weatherCode == 800) return Label.SUNNY;
        if( weatherCode >= 800 && weatherCode < 900) return Label.CLOUDS;
        return Label.UNDEFINED;
    }

    /**
     * 
     * return Label according to temperature retrieved from API call
     * 
     * @param temp
     * @return
     */
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
