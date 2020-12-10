package model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "typ")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Commerce.class, name = "commerce"),
	}
)
@JsonTypeName("store")
public abstract class Store {
	long id;
	String name;
	String address;
	protected List<Product> product;
	
	public List<Product> getProduct(){
		return product;
	}
	public void makeJSON(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.NON_PRIVATE);
		try{
		objectMapper.writeValue(new File("src/main/java/restService/content/store.json"), this);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getName(){
		return name;
	}
}
