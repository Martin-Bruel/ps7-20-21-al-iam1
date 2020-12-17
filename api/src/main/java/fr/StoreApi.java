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
@RequestMapping("/store")
public class StoreApi {

    private final StoreRepository storeRepository;

    StoreApi (StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    @GetMapping("/")
    List<Store> allStore(){
        return storeRepository.findAll();
    }
}
