package com.example.polyville2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("shop")
public class Shop extends Store{
	@JsonCreator
	public Shop(@JsonProperty("id") int id,@JsonProperty("name") String name,@JsonProperty("address") String address, @JsonProperty("products") List<Product> products) {
		this.id=id;
		this.address=address;
		this.name=name;
		if (products!=null)this.products=products;
		else this.products=new ArrayList<Product>();
	}
	
	public Shop(String name,String address) {
		this.address=address;
		this.name=name;
		this.products=new ArrayList<Product>();
	}
	public void addProduct(Product i) {
		products.add(i);
	}


}