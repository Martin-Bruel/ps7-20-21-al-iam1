package model;

public class Item extends Product{
	
	private float price;
	
	public Item(String name,float price) {
		this.price=price;
		this.name=name;
	}
	public float getPrice(){
		return price;
	}
	
}
