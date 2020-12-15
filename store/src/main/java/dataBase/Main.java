package dataBase;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.*;


public class Main {
	public static void main(String[] args) {
		
		Store shop=new Shop(0,"truc2","adresse truc",null,null);
		shop.addProduct(new Item("item1", 13));
		Publication p1=new Publication("Vente de parapluie","Aujourd'hui il pleut, si tu as besoin d'un parapluie n'hésite pas à passer à notre magasin. MagasinPluie",null);
		p1.addLabel(Label.BAD_WEATHER);
		shop.addPublication(p1);
		shop.makeJSON();
		//System.out.println(shop.detailsToJSON());
		//System.out.println(shop.toJSON());
		//System.out.println(shop.publicationsToJSON());
		//shop.printJSON();
		//shop = null;
		//System.out.println("shop"+shop);
		//shop = JsonReader.read();
		//System.out.println("shop"+shop);
		//System.out.println(LocalDateTime.now());
	}
}
