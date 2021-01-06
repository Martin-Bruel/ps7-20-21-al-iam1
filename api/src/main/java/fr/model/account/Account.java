package fr.model.account;

import fr.Main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class Account {
	
	private  @Id@GeneratedValue Long id;

    @Column(unique = true)
	private String username;
	private String password;
	private String cardNumber;
	private double balanceAccount;
	
	public Account() {
		
	}

	public Account(String username, String password) {
		this.username = username;
		this.cardNumber = "none";
		this.balanceAccount = 0;
		this.password=Main.encoder.encode(password);
	}
	
	public String getUsername() {
		return username;
	}
	
	public Long getID() {
		return id;
	}

	public double getBalanceAccount(){
		return this.balanceAccount;
	}

	public void setCardNumber(String cardnb){
		this.cardNumber = cardnb;
	}
	
	public void setBalanceAccount(double amount){
		this.balanceAccount = amount;
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
	 * 
	 * @param amount
	 * increment Balance account to the amount given in parameter
	 */
	public void creditBalanceAccount(double amount){
		this.balanceAccount += amount;
	}
	
	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", cardNumber='" + cardNumber + '\'' +
				", balanceAccount='" + balanceAccount +'\'' +
				'}';
	}
}
