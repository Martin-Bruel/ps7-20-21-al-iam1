package fr;


import fr.model.store.Store;
import fr.model.store.StoreRepository;
import fr.model.traffic.Traffic;
import fr.model.traffic.TrafficRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Server {

    private final TrafficRepository trafficRepository;
    private final StoreRepository storeRepository;


    Server (TrafficRepository trafficRepository, StoreRepository storeRepository){
        this.trafficRepository = trafficRepository;
        this.storeRepository = storeRepository;
    }


    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/traffic/date/{year}/{month}/{day}")
    List<Traffic> trafficForDate(@PathVariable int year, @PathVariable int month, @PathVariable int day){
        return trafficRepository.findAll().stream().filter(traffic -> traffic.getDate().isEqual(LocalDate.of(year, month, day))).collect(Collectors.toList());
    }

    @GetMapping("/traffic/store/{storeId}")
    List<Traffic> trafficForStore(@PathVariable long storeId){
        return trafficRepository.findAll().stream().filter(traffic -> traffic.getStoreId()==storeId).collect(Collectors.toList());
    }

    @GetMapping("/store")
    List<Store> allStore(){
        return storeRepository.findAll();
    }

    @PostMapping("/traffic")
    Traffic newTraffic(@RequestBody Traffic newTraffic) {
        return trafficRepository.save(newTraffic);
    }




}
