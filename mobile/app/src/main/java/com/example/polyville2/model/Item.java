package com.example.polyville2.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("item")

public class Item extends Product{
	
	private float price;
	
	@JsonCreator
	public Item(@JsonProperty("name") String name,@JsonProperty("price") float price) {
		this.price = price;
		this.name = name;
	}

	//public Item(String name,float price) {
	//	this.price=price;
	//	this.name=name;
	//}
	public float getPrice(){
		return price;
	}
	
}
