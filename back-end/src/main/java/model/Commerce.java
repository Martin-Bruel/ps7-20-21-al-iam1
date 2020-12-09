package model;

import java.util.ArrayList;

public class Commerce extends Store{
	
	public Commerce(String name,String address){
		this.name=name;
		this.address=address;
		this.product= new ArrayList<Product>();
		
	}	
	
}
