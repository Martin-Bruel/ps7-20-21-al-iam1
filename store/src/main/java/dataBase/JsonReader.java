package dataBase;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Store;

public class JsonReader {
	
	public static Store read() {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			Store store = mapper.readValue(new File("src/main/java/dataBase/content/store.json"), Store.class);
			return store;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
