package restService;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Shop { // on peut faire de l'héritage
    private final long id;
    private String name;
    private String address;
    ShopType shopType;
    String contentJSON;
    File file;
    JSONObject shop;
    JSONObject header;
    JSONArray publications;

    

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
        header = new JSONObject();
        shop = new JSONObject();
        publications = new JSONArray();
        header.put("id",id);
        header.put("name",name);
        header.put("address",address);
        header.put("type", shopType.toString());
        JSONObject publication = new JSONObject();
        publication.put("title","tadaaaa");
        publication.put("date","10/11/2202");
        publications.add(publication);
        shop.put("header",header);
        shop.put("publications",publications);
        return shop.toJSONString();
    }

    long getID(){
        return id;
    }

    void createJSON(){ 
        header = new JSONObject();
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
