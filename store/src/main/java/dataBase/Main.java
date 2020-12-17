package dataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.IncorrectUnitWeatherAPIException;

import java.time.LocalTime;

import model.*;
import weather.apis.OpenWeatherAPI;

public class Main {
	public static void main(String[] args) throws IOException, IncorrectUnitWeatherAPIException {
		ArrayList<Double> address = new ArrayList<>();
		address.add(43.61563752169879);
		address.add(7.071778197708522);
		OpenWeatherAPI api = new OpenWeatherAPI("metric");
		Store shop = new Shop(0, "truc2", address, null, null, null, null, api);
	
		shop.addProduct(new Item("item1", 13));

		Publication p1 = new Publication("Vente de parapluie","Aujourd'hui il pleut, si tu as besoin d'un parapluie n'hésite pas à passer à notre magasin. MagasinPluie",null);
		p1.addLabel(Label.RAIN);
		shop.addPublication(p1);

		Publication p2 = new Publication("Chocolat chaud", "Vous avez un peu froid ? N'hésitez pas à venir vous réchauffer auprès d'un chocolat chaud chez nous ! MagasinPluie", null);
		p2.addLabel(Label.CHILLY);
		p2.addLabel(Label.COLD);
		shop.addPublication(p2);

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
