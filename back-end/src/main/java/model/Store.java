package model;

import java.util.List;

public abstract class Store {
	long id;
	String name;
	String address;
	List<Product> product;
	
	public List<Product> getProduct(){
		return product;
	}
	
	
}
