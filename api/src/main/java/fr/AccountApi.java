package fr;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import fr.model.account.Account;
import fr.model.account.AccountRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/users")
public class AccountApi { 
	private final AccountRepository accountRepository;
	
	AccountApi(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@GetMapping("/create/{username}/{password}")
	boolean createAccount(@PathVariable String username,@PathVariable String password) {
		System.out.println("account "+username+" "+password); 
		Optional<Account> optT = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username)).findFirst();
		if(optT.isEmpty()) {
			accountRepository.save(new Account(username,password));
			return true;
		}
		return false;
	}
	
	@GetMapping("/connect/{username}/{password}")
	Long connection(@PathVariable String username,@PathVariable String password) {
		System.out.println("account "+username+" "+password); 
		Optional<Account> optT = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username) && account.isGoodPasword(password)).findFirst();
		if(optT.isEmpty()) {
			return null;
		}
		return optT.get().getID();
	}

	
    @GetMapping("/")
	List<Account> allAccount(){
		return accountRepository.findAll();
	}
}
