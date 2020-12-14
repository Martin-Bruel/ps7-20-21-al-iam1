package com.example.polyville2.model;

import android.os.Parcelable;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Shop.class, name = "shop"),
	}
)
@JsonTypeName("store")
public abstract class Store implements  Serializable {
	long id;
	String name;
	String address;
	protected List<Product> products;
	
	public List<Product> getProducts(){
		return products;
	}

	public void addProduct(Product i) {
		products.add(i);
	}
	public String toJSON() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		try{
			return mapper.writeValueAsString(this);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String detailsToJSON(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		String result="";
		try{
			result=result+ mapper.writeValueAsString(name);
			result=result+ mapper.writeValueAsString(id);
			//result=result+ mapper.writeValueAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public String productsToJSON(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		try{
			return mapper.writeValueAsString(products);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void makeJSON(){
		ObjectMapper objectMapper = new ObjectMapper();
		try{
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/java/dataBase/content/store.json"), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getName(){
		return name;
	}
}
