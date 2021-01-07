package fr.model.transaction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Transaction {

    private  @Id@GeneratedValue Long id;
    private Long accountId;
    private double amount;
    private int storeId;
    private LocalDate date;
    private String currencyType;

    public Transaction(Long accountId, double amount, String currencyType, int storeId, LocalDate date) {
        this.accountId = accountId;
        this.amount = amount;
        this.storeId = storeId;
        this.date = date;
        this.currencyType = currencyType;
    }

    public Transaction(){}

    public Long getAccountId() {
        return accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCurrencyType() {
        return currencyType;
    }
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
