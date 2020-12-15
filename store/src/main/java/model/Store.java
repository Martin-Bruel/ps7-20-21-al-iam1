package model;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.List;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

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
	
	public List<Product> getProducts(){
		return products;
	}

	public void addProduct(Product i) {
		products.add(i);
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
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
		String result="";	
		try{
			String s=this.getClass().getSimpleName();
			s=s.replaceFirst(s.charAt(0)+"",(s.charAt(0)+"").toLowerCase());
			result+="{\"type\":\""+s+"\"";
			result+= ",\"id\":"+mapper.writeValueAsString(this.id);
			result+= ",\"name\":"+mapper.writeValueAsString(this.name);
			result+= ",\"address\":"+mapper.writeValueAsString(this.address)+"}";
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

	public void makeJSON(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try{
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/java/dataBase/content/store.json"), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public	String getBluetoothMac() {
		LocalDevice local;
		try {
			local = LocalDevice.getLocalDevice();
		return local.getBluetoothAddress();		} catch (BluetoothStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public String getName(){
		return name;
	}

	public int getId() {
		return id;
	}
}
