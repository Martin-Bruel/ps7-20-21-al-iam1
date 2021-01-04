package weather.urlbuilder;

/**
 * Pattern builder to construct url to make it easy to adapt to whatever API and parameters
 */
public class StringUrlBuilder {
    private String root;
    private String key;
    private String lat;
    private String lon;
    private String units;

    public StringUrlBuilder(){}

    public StringUrlBuilder withLat(String lat){
        this.lat = lat;
        return this;
    }

    public StringUrlBuilder withLon(String lon){
        this.lon = lon;
        return this;
    }

    public StringUrlBuilder withUnits(String unit){
        this.units = unit;
        return this;
    }

    public StringUrlBuilder withRoot(String apiDomain){
        this.root = apiDomain;
        return this;
    }

    public StringUrlBuilder withKey(String key){
        this.key = key;
        return this;
    }

    public StringUrl build(){
        return new StringUrl(this);
    }

    public String getRoot(){
        return this.root;
    }

    public String getKey(){
        return this.key;
    }

    public String getLat(){
        return this.lat;
    }

    public String getLon(){
        return this.lon;
    }

    public String getUnits(){
        return this.units;
    }
}
