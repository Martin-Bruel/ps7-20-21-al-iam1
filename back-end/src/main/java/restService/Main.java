package restService;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;

import model.*;


public class Main {
	public static void main(String[] args) {
		Store shop=new Commerce("truc1","adresse truc");
		shop.makeJSON();
		shop = null;
		System.out.println("shop"+shop);
		shop = JsonReader.read();
		System.out.println("shop"+shop);
		System.out.println(LocalDateTime.now());
	}
}
