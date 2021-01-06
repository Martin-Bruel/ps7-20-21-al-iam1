package fr.model.account;

import fr.Main;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "user")
public class Account {
	
	private @Id@GeneratedValue@Column(name = "account_id") Long id;

    @Column(unique = true)
	private String username;
	private String password;
	private String cardNumber;

	@OneToMany(mappedBy = "account", cascade = {CascadeType.ALL})
	private List<Currency> currencies;
	
	public Account() {}

	public Account(String username, String password) {
		this(username, password, new ArrayList<>());
	}
	public Account(String username, String password, List<Currency> currencies) {
		this.username = username;
		this.cardNumber = "none";
		this.currencies = currencies;
		this.password=Main.encoder.encode(password);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}
	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}

	/**
	 * @param amount
	 * increment Balance account to the amount given
	 */
	public void creditBalance(double amount, String type){

		Currency currency = findCurrencyByName(type);
		if (currency == null) {
			currency = new Currency(amount, type);
			currencies.add(currency);
			currency.setAccount(this);
		}
		else currency.creditBalance(amount);
	}

	/**
	 * @param amount
	 * decrement Balance account to the amount given
	 */
	public void debitBalance(double amount, String type) throws IllegalArgumentException{

		Currency currency = findCurrencyByName(type);
		if (currency == null) throw new IllegalArgumentException();
		else currency.debitBalance(amount);
	}

	/**
	 * 
	 * @param passwordType
	 * @return boolean
	 * 
	 * checks if the given password is the same as the account password. Return true if yes, else false.
	 * Password is encoding for security
	 */
	public boolean isGoodPasword(String passwordType) {
		return Main.encoder.matches(passwordType, password);
	}


	/**
	 * Return Currency if exist
	 * @param type of currency
	 * @return currency
	 */
	public Currency findCurrencyByName(String type){
		return currencies.stream().filter(currency -> currency.getType().equals(type)).findFirst().orElse(null);
	}

	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", cardNumber='" + cardNumber + '\'' +
				", currencies=" + currencies +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, cardNumber, currencies);
	}
}
