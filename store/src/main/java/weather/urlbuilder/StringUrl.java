package weather.urlbuilder;

public class StringUrl {
    String root;
    String key;
    String lat;
    String lon;
    String units;

    public StringUrl(StringUrlBuilder stringUrlBuilder){
        this.root = stringUrlBuilder.getRoot();
        this.key = stringUrlBuilder.getKey();
        this.lat = stringUrlBuilder.getLat();
        this.lon = stringUrlBuilder.getLon();
        this.units = stringUrlBuilder.getUnits();
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
