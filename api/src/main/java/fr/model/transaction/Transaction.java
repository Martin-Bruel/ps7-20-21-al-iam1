package fr.model.transaction;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

public class Transaction {

    private  @Id@GeneratedValue Long id;
    private int accountId;
    private int amount;
    private int storeId;
    private LocalDate date;

    public Transaction(int accountId, int amount, int storeId, LocalDate date) {
        this.accountId = accountId;
        this.amount = amount;
        this.storeId = storeId;
        this.date = date;
    }

    /**
     * update account amount
     */
    public void process(){

    }



    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
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
}
