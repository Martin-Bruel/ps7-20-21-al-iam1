package restService;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Server {
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/hello-world")
	@ResponseBody
	public String shop(@RequestParam(value = "name",defaultValue = "") String name){
		Shop shop = new Shop(counter.incrementAndGet(),ShopType.COMMERCE, name, "10 avenue Tour");
		return shop.getJSON();
	}
}
