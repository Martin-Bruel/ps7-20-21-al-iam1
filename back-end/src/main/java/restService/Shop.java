package restService;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

public class Shop {
    private final long id;
    private String name;
    private String address;
    ShopType shopType;
    String contentJSON;

    Shop(long id, ShopType type, String name,String address){
        this.id=id;
        shopType=type;
        this.address=address;
        this.name=name;
        contentJSON=String.valueOf(id)+"content.json";
        createJSON();
    }

    long getID(){
        return id;
    }

    void createJSON(){ // héritage plus tard
        JSONObject header = new JSONObject();
        header.put("id",id);
        header.put("name",name);
        header.put("address",address);
        header.put("type", shopType);
        try (FileWriter file = new FileWriter("/content/"+contentJSON)) {
 
            file.write(header.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
