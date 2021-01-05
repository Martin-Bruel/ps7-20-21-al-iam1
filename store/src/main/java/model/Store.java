package model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import com.fasterxml.jackson.datatype.jsr310.*;

import exceptions.IncorrectUnitWeatherAPIException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;


import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

import weather.apis.OpenWeatherAPI;
import weather.apis.WeatherAPI;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = Shop.class, name = "shop"), })
@JsonTypeName("store")
@JsonIgnoreProperties(value = { "api", "weather" })
public abstract class Store {
	int id;
	String name;
	List<Double> address;
	List<Currency> localCurrencies;	
	protected List<Product> products;
	OpeningHours openingHours;
	List<Publication> allPublications;
	List<Publication> contextPublications;
	protected WeatherAPI api;
	protected List<Label> weather;


	public List<Double> getAddress() {
		return this.address;
	}

	public void setAddress(List<Double> position) {
		this.address = position;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void addProduct(Product i) {
		this.products.add(i);
	}

	public List<Publication> getContextPublications() {
		return this.contextPublications;
	}

	public void setContextPublications(List<Publication> publis) {
		this.contextPublications = publis;
	}

	public List<Publication> getAllPublications() {
		return this.allPublications;
	}

	public void setAllPublications(List<Publication> allPublications) {
		this.allPublications = allPublications;			//result += ",\"open\":" + mapper.writeValueAsString(this.openingHours.isOpen());
	}

	public void addPublication(Publication i) {
		allPublications.add(i);
		for (Label label : i.labels) {
			if (this.weather.contains(label)) {
				this.contextPublications.add(i);
			}
		}
	}

	public List<Currency> getLocalCurrencies(){
		return localCurrencies;
	}

	public void addCurrency(Currency currency){
		localCurrencies.add(currency);
	}

	public void setLocalCurrencies(List<Currency> currencies){
		localCurrencies=currencies;
	}

	public OpeningHours getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(OpeningHours openingHours) {
		this.openingHours = openingHours;
	}

	public WeatherAPI getApi() {
		return this.api;
	}

	public void setApi(WeatherAPI api) {
		this.api = api;
	}

	public List<Label> getWeather() {
		return this.weather;
	}

	public void setWeather(List<Label> weather) {
		this.weather = weather;
	}

	public String toJSON() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
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
		String result = "";
		try {
			String s = this.getClass().getSimpleName();
			s = s.replaceFirst(s.charAt(0) + "", (s.charAt(0) + "").toLowerCase());
			result += "{\"type\":\"" + s + "\"";
			result += ",\"id\":" + mapper.writeValueAsString(this.id);
			result += ",\"name\":" + mapper.writeValueAsString(this.name);
			result += ",\"address\":" + mapper.writeValueAsString(this.address);
			result += ",\"openingHours\":" + mapper.writeValueAsString(this.openingHours);
			result += ",\"localCurrencies\":" + mapper.writeValueAsString(this.localCurrencies) +"}";
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
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		try {
			return mapper.writer().writeValueAsString(allPublications);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnore
	public String getBluetoothMac() {
		String mac = null;
		try {
			mac = LocalDevice.getLocalDevice().getBluetoothAddress();
		} catch (BluetoothStateException e) {
			mac = "";
		}

		return mac;
    }

	public String contextPublicationsToJSON() {
		try {
			this.setContextPublication();
		} catch (IncorrectUnitWeatherAPIException e1) {
			e1.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
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
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.NON_PRIVATE);
		try {
			objectMapper.writerWithDefaultPrettyPrinter()
					.writeValue(new File("src/main/java/dataBase/content/store.json"), this);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setWeatherAPI(WeatherAPI api) {
		this.api = api;
	}

	public void setWeatherLabel() throws IncorrectUnitWeatherAPIException {
		if(this.api == null) this.api = new OpenWeatherAPI("metric");
		this.weather = this.api.callApi(this.address.get(0), this.address.get(1));
	}

	public void setContextPublication() throws IncorrectUnitWeatherAPIException {
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
