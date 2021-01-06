package com.example.polyville2.model;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import android.bluetooth.BluetoothDevice;
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
@JsonSubTypes({ @JsonSubTypes.Type(value = Shop.class, name = "shop"), })
@JsonTypeName("store")
@JsonIgnoreProperties({"MACaddress", "bluetoothDevice"})
public abstract class Store implements Serializable{
	int id;
	String name;
	List<Double> address;
	List<Currency> localCurrencies;
	protected List<Product> products;
	OpeningHours openingHours;
	List<Publication> allPublications;
	List<Publication> contextPublications;
	protected List<Label> weather;
	@JsonIgnore
	String MACaddress;


	public String getMACaddress(){
		return this.MACaddress;
	}

	public void setMACaddress(String MACaddress) {
		this.MACaddress = MACaddress;
	}

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
		this.allPublications = allPublications;
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
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		try {
			return mapper.writer().writeValueAsString(allPublications);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public String contextPublicationsToJSON() {
		ObjectMapper mapper = new ObjectMapper();
		// CollectionType publicationListType =
		// mapper.getTypeFactory().constructCollectionType(List.class,Publication.class);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		try {
			return mapper.writer().writeValueAsString(contextPublications);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

}
