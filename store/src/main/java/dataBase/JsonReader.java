package dataBase;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import model.Store;

public class JsonReader {
	
	public static Store read() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			File fileToParse = new File("src/main/java/dataBase/content/store.json");
			Store store = mapper.readValue(fileToParse, Store.class);
			return store;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
