package fr.model.account;

import javax.persistence.*;

@Entity
public class Currency {

    private double balance;
    private String name;
    @Id@GeneratedValue
    private Long id;
    private String type;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="account_id")
    private Account account;

    public Currency(double balance, String name) {
        this.balance = balance;
        this.name = name;
        this.type = "Currency";
    }

    public Currency(){}

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param amount
     * increment Balance account to the amount given
     */
    public void creditBalance(double amount){
        this.balance+=amount;
    }

    /**
     * decrement balance to the amount given
     * @param amount
     */
    public void debitBalance(double amount) throws IllegalArgumentException{
        if (balance < amount) throw new IllegalArgumentException("Not enough balance");
        this.balance-=amount;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }
}
