package fr;

import fr.model.account.Account;
import fr.model.account.AccountRepository;
import fr.model.account.Currency;
import fr.model.account.CurrencyRepository;
import fr.model.store.Store;
import fr.model.store.StoreRepository;
import fr.model.traffic.Traffic;
import fr.model.traffic.TrafficRepository;
import fr.model.transaction.TransactionRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@ComponentScan("fr.model.traffic")
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final LocalDate date = LocalDate.now();

    @Bean
    CommandLineRunner initDatabase(TrafficRepository trafficRepository, StoreRepository storeRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, CurrencyRepository currencyRepository) {

        return args -> {

            log.info("Preloading " + trafficRepository.save(new Traffic("Tout a 1 euros", 3, date.minusDays(2), 1)));
            log.info("Preloading " + trafficRepository.save(new Traffic("Tout a 1 euros", 100, date.minusDays(1), 1)));
            log.info("Preloading " + trafficRepository.save(new Traffic("Tout a 1 euros", 0, date, 1)));

            log.info("Preloading " + trafficRepository.save(new Traffic("Gifi la mode", 8, date.minusDays(2), 2)));
            log.info("Preloading " + trafficRepository.save(new Traffic("Gifi la mode", 15, date.minusDays(1), 2)));
            log.info("Preloading " + trafficRepository.save(new Traffic("Gifi la mode", 36, date, 2)));

            log.info("Preloading " + trafficRepository.save(new Traffic("Les fleurs de la cabane au fond du jardin", 4, date.minusDays(2), 3)));
            log.info("Preloading " + trafficRepository.save(new Traffic("Les fleurs de la cabane au fond du jardin", 2, date.minusDays(1), 3)));
            log.info("Preloading " + trafficRepository.save(new Traffic("Les fleurs de la cabane au fond du jardin", 5, date, 3)));

            log.info("Preloading " + storeRepository.save(new Store(1234,"Tout a 1 euros")));
            log.info("Preloading " + storeRepository.save(new Store(1235,"Gifi la mode")));
            log.info("Preloading " + storeRepository.save(new Store(1236,"Les fleurs de la cabane au fond du jardin")));
            log.info("Preloading " + storeRepository.save(new Store(0,"Coffee and Shop")));
            

            log.info("Preloading " + accountRepository.save(new Account("user1","Coffee and Shop")));

            Account user1 = new Account("user2","admin");
            user1.creditBalance(4, "POLYCOIN");
            user1.creditBalance(10, "OUI");
            log.info("Preloading " + accountRepository.save(user1));
        };
    }
}
