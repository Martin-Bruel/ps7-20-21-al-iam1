package dataBase;

//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

import model.*;


public class Main {
	public static void main(String[] args) {
		
		Store shop=new Shop(0,"truc2","adresse truc",null,null,null);
		shop.addProduct(new Item("item1", 13));
		Publication p1=new Publication("Vente de parapluie","Aujourd'hui il pleut, si tu as besoin d'un parapluie n'hésite pas à passer à notre magasin. MagasinPluie",null);
		p1.addLabel(Label.BAD_WEATHER);
		shop.addPublication(p1);
		List<LocalTime>hours=new ArrayList<>();
		hours.add(LocalTime.now().withHour(8).withMinute(0));
		hours.add(LocalTime.now().withHour(12).withMinute(0));
		hours.add(LocalTime.now().withHour(14).withMinute(0));
		hours.add(LocalTime.now().withHour(15).withMinute(25));
		OpeningHours openingHours=new OpeningHours(hours,hours,hours,hours,hours,hours,hours);
		shop.setOpeningHours(openingHours);
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
