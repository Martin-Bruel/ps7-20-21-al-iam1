package weather.model;


import weather.apis.OpenWeatherAPI;
import weather.apis.WeatherAPI;
import weather.apis.WeatherBitAPI;
import exceptions.UndefinedWeatherAPIException;

/**
 * 
 * Enum of APIs known by the system. 
 * For the developer: add here your API and its key as constructor
 * Then implement corresponding methods 
 * 
 */
public enum WeatherAPIEnum {
    OPENWEATHER("b7a405fea8328d8c19c034d9a8b673aa"),
    WEATHERBIT("50b12870871b4b03b6d77bea032c508c");

    public final String key;

    private WeatherAPIEnum(String key){
        this.key = key;
    }

    public WeatherAPI getApi() throws UndefinedWeatherAPIException {
        WeatherAPI res;
        String key = this.key;
        switch(this){
            case OPENWEATHER:
                res = new OpenWeatherAPI(key);
                break;
            case WEATHERBIT: 
                res = new WeatherBitAPI(key);
                break;
            default:
                throw new UndefinedWeatherAPIException("This API is unknown. Please, add it and its method to package \"apis\".");
        }
        return res;
    }


}
