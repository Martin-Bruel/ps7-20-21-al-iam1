package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "typ")
@JsonTypeName("commerce")
public class Commerce extends Store{
	@JsonCreator
	public Commerce(@JsonProperty("id") int id,@JsonProperty("name") String name,@JsonProperty("address") String address, @JsonProperty("product") List<Product> products) {
		this.id=id;
		this.address=address;
		this.name=name;
		this.product=products;
	}
	
	public Commerce( String name,String address) {
		this.address=address;
		this.name=name;
		this.product=new ArrayList<>();
	}
	public void addItem(Item i) {
		product.add(i);
	}
}
