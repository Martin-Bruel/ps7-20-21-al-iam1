package dataBase;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import model.Store;
/**
 * Read the file json who represents the store
 * @author Martin Bruel, Florian Striebel, Maeva Lecavalier, Nicolas Lanoux 
 *
 */
public class JsonReader {
	
	public static Store read() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			Store store = mapper.readValue(new File("src/main/java/dataBase/content/store.json"), Store.class);
			return store;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
