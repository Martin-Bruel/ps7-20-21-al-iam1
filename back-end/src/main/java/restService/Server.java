package restService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Server {
	private final AtomicLong counter = new AtomicLong();
	private final List<Shop> shops = new ArrayList<Shop>();

	
	Server() {
		for(int i=0;i<5;i++)
		shops.add(new Shop(i,ShopType.COMMERCE, "name"+String.valueOf(i), String.valueOf(10+i)+" avenue Tour"));
	  }
	

	@GetMapping("/hello-world")
	@ResponseBody
	public String shop(@RequestParam(value = "name",defaultValue = "") String name){
		Shop shop = new Shop(counter.incrementAndGet(),ShopType.COMMERCE, name, "10 avenue Tour");
		return shop.getJSON();
	}

	@GetMapping("/shop/{id}")
	String one(@PathVariable Long id) {
		for(Shop s: shops){
			if(s.getID()==id)
			return s.getJSON();
		}
		return "";
	}

}
