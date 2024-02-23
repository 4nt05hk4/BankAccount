package jpa1;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double value;

    private TYPE currency;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private Client sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private Client recipient;

    private Date date;

    public Transaction() {
    }

    public Transaction(double value, TYPE currency, Client sender, Client recipient) {
        this.value = value;
        this.currency = currency;
        this.sender = sender;
        this.recipient = recipient;
        this.date = new Date();
    }

    public long getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public TYPE getCurrency() {
        return currency;
    }

    public void setCurrency(TYPE currency) {
        this.currency = currency;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public Client getRecipient() {
        return recipient;
    }

    public void setRecipient(Client recipient) {
        this.recipient = recipient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", value=" + value +
                ", currency=" + currency +
                ", client1=" + sender +
                ", client2=" + recipient +
                ", date=" + date +
                '}';
    }
}
