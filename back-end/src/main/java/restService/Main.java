package restService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	String home() {
		return "ok";
	}
	public static void main(String[] args) {
		SpringApplication.run(Main.class,args);

		Shop testShop=new Shop(0,ShopType.COMMERCE, "name", "10 avenue Tour");
		System.out.println(testShop.getID());

	}
}
