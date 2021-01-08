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
		// adresse de Polytech
		address.add(43.61563752169879);
		address.add(7.071778197708522);
		OpenWeatherAPI api = new OpenWeatherAPI("metric");
		Store shop = new Shop(0, "Coffee and Shop", address, null, null, null, null, api);
	
		shop.addProduct(new Item("Umbrella", 20));
		shop.addProduct(new Item("K-way", 35));
		shop.addProduct(new Item("Sunglasses", 15));
		shop.addProduct(new Item("Hat", 5));
		shop.addProduct(new Item("Beer", 2));
		shop.addProduct(new Item("Coffee", 0.5F));
		shop.addProduct(new Item("Tea", 0.6F));

		Publication p1 = new Publication("Umbrella sales","It's raining. If you need an umbrella you can visit our shop.",null);
		p1.addLabel(Label.RAIN);
		shop.addPublication(p1);

		Publication p2 = new Publication("Hot Chocolate", "You're cold? You come to our shop and have a hot chocolate !", null);
		p2.addLabel(Label.CHILLY);
		p2.addLabel(Label.COLD);
		shop.addPublication(p2);

		Publication p3 = new Publication("Sunglasses", "Too sunny, it stings the eyes ! Come to us and get sunglasses ! ", null);
		p3.addLabel(Label.SUNNY);
		shop.addPublication(p3);

		Publication p4 = new Publication("Clouds : be far-sighted"," Come buy an umbrella to our shop before the rain ! ", null);
		p4.addLabel(Label.CLOUDS);
		shop.addPublication(p4);

		List<LocalTime>hours=new ArrayList<>();
		hours.add(LocalTime.now().withHour(8).withMinute(0));
		hours.add(LocalTime.now().withHour(12).withMinute(0));
		hours.add(LocalTime.now().withHour(14).withMinute(0));
		hours.add(LocalTime.now().withHour(15).withMinute(25));
		OpeningHours openingHours=new OpeningHours(hours,hours,hours,hours,hours,hours,hours);
		shop.setOpeningHours(openingHours);
		shop.addCurrency(Currency.POLYCOIN);
		shop.makeJSON();
	}
}
