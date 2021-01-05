package fr.model.account;

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
	
	public Account() {
		
	}
	public Account(String username, String password) {
		this.username=username;
		this.password=password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Long getID() {
		return id;
	}
	public boolean IsGoodPasword(String passwordType) {
		return passwordType.equals(password);
	}
	
}
