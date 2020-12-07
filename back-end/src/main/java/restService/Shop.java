package restService;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import org.json.simple.JSONObject;

public class Shop { // on peut faire de l'héritage
    private final long id;
    private String name;
    private String address;
    ShopType shopType;
    String contentJSON;
    File file;

    Shop(long id, ShopType type, String name,String address){
        this.id=id;
        shopType=type;
        this.address=address;
        this.name=name;
        contentJSON="src/main/java/restService/content/"+String.valueOf(id)+"content.json";
        file = new File(contentJSON);
        createJSON();
    }
    
    String getJSON(){
        JSONObject header = new JSONObject();
        header.put("id",id);
        header.put("name",name);
        header.put("address",address);
        header.put("type", shopType.toString());
        return header.toJSONString();
    }

    long getID(){
        return id;
    }

    void createJSON(){ 
        JSONObject header = new JSONObject();
        header.put("id",id);
        header.put("name",name);
        header.put("address",address);
        header.put("type", shopType.toString());
        
        try {
			if (!file.exists())
				file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(header.toJSONString());
			writer.flush();
			writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
