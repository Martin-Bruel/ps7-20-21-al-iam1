package com.example.polyville2.model;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    private int id;
    private String username;
    private String password;
    private String cardNumber;
    private List<Currency> currencies;

    @JsonCreator
    public Account(@JsonProperty("id") int id,@JsonProperty("username") String username,@JsonProperty("password") String password, @JsonProperty("cardNumber") String cardNumber,
    @JsonProperty("currencies") List<Currency> currencies) {
        this.id=id;
        this.username = username;
        this.password=password;
        this.cardNumber=cardNumber;
        this.currencies=currencies;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }


    public void creditBalance(double amount, Currency type){
        int index = currencies.indexOf(type);
        if (index!=-1) {
            currencies.get(index).setBalance(amount);
        }
    }

    public void debitBalance(double amount, Currency type) throws IllegalArgumentException{
        int index = currencies.indexOf(type);
        if (index!=-1) {
            currencies.get(index).debitBalance(amount);
        }
    }

    public boolean canPay(double price, String currency){

        for (Currency c: currencies) {
            if(c.getType().equals(currency) && price <= c.getBalance()) return true;
        }
        return false;
    }

}


