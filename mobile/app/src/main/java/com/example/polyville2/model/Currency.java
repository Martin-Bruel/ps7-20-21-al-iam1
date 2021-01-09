package com.example.polyville2.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

public class Currency implements Serializable {
    private double balance;
    private String name;

    public Currency(@JsonProperty("balance") double balance, @JsonProperty("name") String name) {
        this.balance = balance;
        this.name = name;
    }

    public Currency(){}

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getType() {
        return name;
    }
    public void setType(String name) {
        this.name = name;
    }


    public void creditBalance(double amount){
        this.balance+=amount;
    }


    public void debitBalance(double amount) throws IllegalArgumentException{
        if (balance < amount) throw new IllegalArgumentException("Not enough balance");
        this.balance-=amount;
    }
}
