package weather.model;

/**
 * 
 * Enum of APIs known by the system. 
 * For the developer: add here your API and its key as constructor
 * Then implement corresponding methods 
 * 
 */
public enum WeatherAPI {
    OPENWEATHER("b7a405fea8328d8c19c034d9a8b673aa"),
    WEATHERBIT("50b12870871b4b03b6d77bea032c508c");

    public final String key;

    private WeatherAPI(String key){
        this.key = key;
    }

}
