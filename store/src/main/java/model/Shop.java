package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import weather.apis.WeatherAPI;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("shop")
@JsonIgnoreProperties(value = {"api"})
public class Shop extends Store{


	@JsonCreator
	public Shop(@JsonProperty("id") int id, 
	@JsonProperty("name") String name,
	@JsonProperty("address") List<Double> address, 
	@JsonProperty("products") List<Product> products, 
	@JsonProperty("openingHours") OpeningHours hours, 
	@JsonProperty("allPublications") List<Publication> allPublications, 
	@JsonProperty("contextPublications") List<Publication>  contextPublications, 
	@JsonProperty("api") WeatherAPI api) {
		this.id = id;
		this.address = address;
		this.name = name;
		if (products != null) this.products=products;
		else this.products = new ArrayList<Product>();
		if (allPublications != null) this.allPublications = allPublications;
		else this.allPublications = new ArrayList<Publication>();
		if (contextPublications != null) this.contextPublications = contextPublications;
		else this.contextPublications = new ArrayList<Publication>();
		openingHours = hours;
		this.api = api;
		this.setWeatherLabel();
	}

	public void addProduct(Product i) {
		products.add(i);
	}
}
