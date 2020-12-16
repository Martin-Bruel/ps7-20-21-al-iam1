package model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Shop.class, name = "shop"),
	}
)
@JsonTypeName("store")
public abstract class Store {
	int id;
	String name;
	String address;
	protected List<Product> products;
	List<Publication> publications;
	OpeningHours openingHours;
	
	public List<Product> getProducts(){
		return products;
	}

	public void addProduct(Product i) {
		products.add(i);
	}

	public List<Publication> getPublications(){
		return publications;
	}

	public void addPublication(Publication i) {
		publications.add(i);
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
		try{
			return mapper.writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String detailsToJSON(){
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

	public String productsToJSON(){
		ObjectMapper mapper = new ObjectMapper();
		CollectionType productListType = mapper.getTypeFactory().constructCollectionType(List.class,Product.class);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		try{
			return mapper.writer().withType(productListType).writeValueAsString(products);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String publicationsToJSON(){
		ObjectMapper mapper = new ObjectMapper();
		//CollectionType publicationListType = mapper.getTypeFactory().constructCollectionType(List.class,Publication.class);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try{
			return mapper.writer().writeValueAsString(publications);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void makeJSON(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		//.registerModule(new ParameterNamesModule())
		//.registerModule(new Jdk8Module())
		//.registerModule(new JavaTimeModule());
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try{
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/java/dataBase/content/store.json"), this);
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
}
