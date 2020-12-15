package fr;


import fr.model.store.Store;
import fr.model.store.StoreRepository;
import fr.model.traffic.Traffic;
import fr.model.traffic.TrafficRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/traffic")
public class TrafficApi {

    private final TrafficRepository trafficRepository;


    TrafficApi(TrafficRepository trafficRepository){
        this.trafficRepository = trafficRepository;
    }

    @GetMapping("/date/{year}/{month}/{day}")
    List<Traffic> trafficForDate(@PathVariable int year, @PathVariable int month, @PathVariable int day){
        return trafficRepository.findAll().stream().filter(traffic -> traffic.getDate().isEqual(LocalDate.of(year, month, day))).collect(Collectors.toList());
    }

    @GetMapping("/store/{storeId}")
    List<Traffic> trafficForStore(@PathVariable long storeId){
        return trafficRepository.findAll().stream().filter(traffic -> traffic.getStoreId()==storeId).collect(Collectors.toList());
    }

    @PostMapping("/")
    Traffic addTraffic(@RequestBody Traffic newTraffic) {
        Optional<Traffic> optT = trafficRepository.findAll().stream().filter((traffic) -> traffic.equals(newTraffic)).findFirst();
        if (optT.isEmpty()) return trafficRepository.save(newTraffic);
        optT.get().add(newTraffic.getNb());
        return trafficRepository.save(optT.get());
    }

    @GetMapping("/")
    List<Traffic> allTraffic() {
        return trafficRepository.findAll();
    }
}
