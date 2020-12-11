package dataBase;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.*;


public class Main {
	public static void main(String[] args) {
		
		Store shop=new Shop(0,"truc2","adresse truc",null);
		shop.addProduct(new Item("item1", 12));
		shop.makeJSON();
		System.out.println(shop.productsToJSON());
		//System.out.println(shop.toJSON());
		//shop.printJSON();
		//shop = null;
		//System.out.println("shop"+shop);
		//shop = JsonReader.read();
		//System.out.println("shop"+shop);
		//System.out.println(LocalDateTime.now());
	}
}
