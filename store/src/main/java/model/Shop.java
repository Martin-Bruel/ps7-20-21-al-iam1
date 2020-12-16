package model;

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
	public Shop(@JsonProperty("id") int id,@JsonProperty("name") String name,@JsonProperty("address") List<Double> address, @JsonProperty("products") List<Product> products, @JsonProperty("all_publications") List<Publication> allPublications) {
		this.id = id;
		this.address = address;
		this.name = name;
		if (products != null) this.products = products;
		else this.products = new ArrayList<Product>();
		if (allPublications != null) {
			this.allPublications = allPublications;
			this.setContextPublication();
		}
		else {
			this.allPublications = new ArrayList<Publication>();
			this.contextPublications = new ArrayList<Publication>();
		}
	}

	public void addProduct(Product i) {
		products.add(i);
	}
}
