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
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return boolean : true if account is created, false if not
	 * 
	 * creates an account with given username and password and saves it to the database
	 * return false if an account with the same username exits
	 */
	@PostMapping("/create")
	boolean createAccount(@RequestBody String username,@RequestBody String password) {
		System.out.println("account "+username+" "+password); 
		Optional<Account> optT = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username)).findFirst();
		if(optT.isEmpty()) {
			accountRepository.save(new Account(username,password));
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return account's id - null
	 * 
	 * Tries connection to account with username and password
	 * If connection has succeeded, returns id of the account logged
	 * else it returns null. 
	 */
	@PostMapping("/connect")
	Long connection(@RequestBody String username,@RequestBody String password) {
		System.out.println("account "+username+" "+password); 
		Optional<Account> optT = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username) && account.isGoodPasword(password)).findFirst();
		if(optT.isEmpty()) {
			return null;
		}
		return optT.get().getID();
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return double
	 * 
	 * returns the amount of the balance account
	 */
	@PostMapping("/balance")
	Double getBalanceAccount(@RequestBody String username, @RequestBody String password){
		System.out.println(username+" requires balance");
		Optional<Account> opt = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username) && account.isGoodPasword(password)).findFirst();
		if(opt.isEmpty()){
			return null;
		}
		return opt.get().getBalanceAccount();
	}

	/**
	 * 
	 * @param amountToAdd
	 * @param username
	 * @param password
	 * @return double
	 * 
	 * adds the amount given in paramater to the current balance account
	 * return the new amount
	 */
	@PostMapping("/balanceIncrement")
	Double incrementBalanceAccount(@RequestBody double amountToAdd, @RequestBody String username, @RequestBody String password){
		System.out.println(username+" increments balance bt "+amountToAdd);
		Optional<Account> opt = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username) && account.isGoodPasword(password)).findFirst();
        if (opt.isEmpty()) {
			return null;
		}
		opt.get().creditBalanceAccount(amountToAdd);
		accountRepository.save(opt.get());
        return opt.get().getBalanceAccount();
	}

	/**
	 * 
	 * @return List<Account>
	 * 
	 * return all accounts existing
	 */
    @GetMapping("/")
	List<Account> allAccount(){
		return accountRepository.findAll();
	}
}
