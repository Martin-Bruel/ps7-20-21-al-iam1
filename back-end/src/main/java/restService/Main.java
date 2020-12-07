package restService;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;

public class Main {
	@RequestMapping("/")
	String home() {
		return "ok";
	}
	public static void main(String[] args) {
		SpringApplication.run(Main.class,args);
	}
}
