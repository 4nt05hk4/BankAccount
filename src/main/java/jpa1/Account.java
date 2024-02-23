package jpa1;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private TYPE currencyType;

    private Double balance;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;


    public Account() {
    }

    public Account(TYPE currencyType, Client client) {
        this.currencyType = currencyType;
        this.balance = 0.0;
        this.client = client;
    }

    public Account(TYPE currencyType, Double balance, Client client) {
        this.currencyType = currencyType;
        this.balance = balance;
        this.client = client;
    }

    public long getAccount_id() {
        return id;
    }

    public TYPE getType() {
        return currencyType;
    }

    public void setType(TYPE currencyType) {
        this.currencyType = currencyType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void addBalance(Double amount) {
        this.balance += amount;
    }

    public boolean subtractBalance(Double amount) {
        if (this.balance > amount) {
            this.balance -= amount;
            return true;
        } else {
            System.out.println("You don't have enough money to complete the transaction");
            return false;
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
