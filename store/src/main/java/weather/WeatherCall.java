package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherCall {

    public static void main(String[] args) throws IOException {

        // it's a GET request, so parameters should be put in url
        URL urlWeatherBit = new URL(weatherBitURL("43.673418120723255", "7.18614731894174"));
        urlCall(urlWeatherBit);
        URL urlOpenWeather = new URL(openWeatherURL("43.673418120723255", "7.18614731894174"));
        urlCall(urlOpenWeather);

    }

    /**
     * 
     * Construct url for weatherBit API
     * 
     * @param lat
     * @param lon
     * @return url
     */
    static String weatherBitURL(String lat, String lon) {
        String key = "50b12870871b4b03b6d77bea032c508c";
        return "https://api.weatherbit.io/v2.0/current?lat=" + lat + "&lon=" + lon + "&key=" + key;
    }

    /**
     * 
     * Construct url OpenWeather API
     * 
     * @param lat : latitude of the place
     * @param lon : lontitude of the place
     * @return url
     */
    static String openWeatherURL(String lat, String lon) {
        String key = "b7a405fea8328d8c19c034d9a8b673aa";
        return "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + key;
    }

    /**
     * 
     * Make a call to given url and print header respoonse and response
     * 
     * @param url
     * @throws IOException
     */
    static void urlCall(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        System.out.println(con.getRequestMethod() + " request to : " + url.toString());
        System.out.println(con.getHeaderField(0));
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // print in String
        String resStr = response.toString();
        System.out.println(resStr + "\n");
    }
}
