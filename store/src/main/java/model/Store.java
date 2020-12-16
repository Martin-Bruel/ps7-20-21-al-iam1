package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import com.fasterxml.jackson.datatype.jsr310.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import weather.apis.WeatherAPI;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = Shop.class, name = "shop"), })
@JsonTypeName("store")
public abstract class Store {
	int id;
	String name;
	List<Double> address;
	protected List<Product> products;
	List<Publication> allPublications;
	List<Publication> contextPublications;
	OpeningHours openingHours;
	private WeatherAPI api;
	private List<Label> weather;
	
	public List<Product> getProducts(){
		return products;
	}

	public void addProduct(Product i) {
		this.products.add(i);
	}

	public List<Publication> getAllPublications() {
		return this.allPublications;
	}

	public void addPublication(Publication i) {
		allPublications.add(i);
		this.setContextPublication();
	}	

	public OpeningHours getOpeningHours(){
		return openingHours;
	}

	public void setOpeningHours(OpeningHours openingHours) {
		this.openingHours = openingHours;
	}

	public String toJSON(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try {
			return mapper.writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String detailsToJSON() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		String result="";	
		try{
			String s=this.getClass().getSimpleName();
			s=s.replaceFirst(s.charAt(0)+"",(s.charAt(0)+"").toLowerCase());
			result+="{\"type\":\""+s+"\"";
			result+= ",\"id\":"+mapper.writeValueAsString(this.id);
			result+= ",\"name\":"+mapper.writeValueAsString(this.name);
			result+= ",\"address\":"+mapper.writeValueAsString(this.address);
			result+= ","+mapper.writeValueAsString(this.openingHours)+"}";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public String productsToJSON() {
		ObjectMapper mapper = new ObjectMapper();
		CollectionType productListType = mapper.getTypeFactory().constructCollectionType(List.class, Product.class);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		try {
			return mapper.writer().withType(productListType).writeValueAsString(products);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String allPublicationsToJSON() {
		ObjectMapper mapper = new ObjectMapper();
		// CollectionType publicationListType =
		// mapper.getTypeFactory().constructCollectionType(List.class,Publication.class);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try {
			return mapper.writer().writeValueAsString(allPublications);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String contextPublicationsToJSON(){
		ObjectMapper mapper = new ObjectMapper();
		// CollectionType publicationListType =
		// mapper.getTypeFactory().constructCollectionType(List.class,Publication.class);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try {
			return mapper.writer().writeValueAsString(contextPublications);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void makeJSON() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		//.registerModule(new ParameterNamesModule())
		//.registerModule(new Jdk8Module())
		//.registerModule(new JavaTimeModule());
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try {
			objectMapper.writerWithDefaultPrettyPrinter()
					.writeValue(new File("src/main/java/dataBase/content/store.json"), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public String getName(){
		return name;
	}

	public int getId() {
		return id;
	}

	public void setWeatherAPI(WeatherAPI api){
		this.api = api;
	}

	public void setWeatherLabel(){
		this.weather = this.api.callApi(this.address.get(0), this.address.get(1), "metric");
	}

	public void setContextPublication(){
		if(this.contextPublications != null) this.contextPublications.clear();
		setWeatherLabel();
		List<Label> weatherLabels = this.weather;
		for (int i = 0; i < this.allPublications.size(); i++){
			Publication publi = this.allPublications.get(i);
			for(Label labelPubli : publi.labels){
				if (weatherLabels.contains(labelPubli)) {
					this.contextPublications.add(publi);
				}
			}
		}
		if(this.contextPublications.isEmpty()) this.contextPublications = this.allPublications;
	}
}
