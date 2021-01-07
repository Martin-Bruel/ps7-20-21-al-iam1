package fr;

import fr.model.account.Account;
import fr.model.account.AccountRepository;

import fr.model.account.Currency;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
	boolean createAccount(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println("account "+username+" "+password); 
		Optional<Account> optT = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username)).findFirst();
		if(optT.isEmpty()) {
			System.out.println(accountRepository.save(new Account(username,password)));
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
	Account connection(@RequestParam("username") String username,@RequestParam("password")String password) {
		System.out.println("account "+username+" "+password); 
		Optional<Account> optT = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username) && account.isGoodPasword(password)).findFirst();
		if(optT.isEmpty()) {
			System.out.println(username + " connexion refused.");
			return null;
		}
		System.out.println(username + " connexion accepted.");
		return optT.get();
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return double
	 * 
	 * returns the amount of the balance account
	 */
	@PostMapping("/balance/{currencyType}")
	double getBalanceAccount(@RequestParam("username") String username, @RequestParam("password")String password, @PathVariable String currencyType){
		System.out.println(username+" requires balance");
		Optional<Account> opt = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username) && account.isGoodPasword(password)).findFirst();
		if(opt.isEmpty()){
			System.out.println(username+" do not exist or wrong password...");
			return 0;
		}
		Currency currency = opt.get().findCurrencyByName(currencyType);
		if (currency != null) return currency.getBalance();
		return 0;
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
	List<Currency> getBalanceAccount(@RequestParam("username") String username, @RequestParam("password")String password){
		System.out.println(username+" requires balance");
		Optional<Account> opt = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username) && account.isGoodPasword(password)).findFirst();
		if(opt.isEmpty()){
			return null;
		}
		return opt.get().getCurrencies();
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
	double incrementBalanceAccount(@RequestParam("amount") double amountToAdd, @RequestParam("currencyType") String currencyType, @RequestParam("username") String username,@RequestParam("password")String password){
		System.out.println(username+" increments balance bt "+amountToAdd);
		Optional<Account> opt = accountRepository.findAll().stream().filter((account) -> account.getUsername().equals(username) && account.isGoodPasword(password)).findFirst();
        if (opt.isEmpty()) {
			return -1;
		}
        Account account = opt.get();
        account.creditBalance(amountToAdd, currencyType);
		accountRepository.save(account);
        return account.findCurrencyByName(currencyType).getBalance();
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
