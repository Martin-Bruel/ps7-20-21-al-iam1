package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Shop extends Store{
	
	public Shop(String name,String address){
		this.name=name;
		this.address=address;
		this.product= new ArrayList<Product>();
		
	}	

	public void makeJSON(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.NON_PRIVATE);
		try{
		objectMapper.writeValue(new File("src/main/java/restService/content/"+name+".json"), this);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
