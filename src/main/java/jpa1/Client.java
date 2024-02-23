package jpa1;

import javax.persistence.*;
import java.util.*;
import jpa1.Transaction;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fullName;
    private Integer age;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>(3);

    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> incomeTransactions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> outcomeTransactions = new ArrayList<>();

    public Client() {
    }

    public Client(String fullName, Integer age) {
        this.fullName = fullName;
        this.age = age;
    }

    public Client(String fullName, Integer age, Account account, Account account1, Account account2) {
        this.fullName = fullName;
        this.age = age;
        accounts.add(account);
        accounts.add(account1);
        accounts.add(account2);
    }

    private void addIncomeTrans(Transaction transaction){
        this.incomeTransactions.add(transaction);
    }

    private void addOutcomeTrans(Transaction transaction){
        this.outcomeTransactions.add(transaction);
    }

    public List<Transaction> getOutcomeTransactions() {
        return outcomeTransactions;
    }

    public List<Transaction> getIncomeTransactions() {
        return incomeTransactions;
    }

    public void setOutcomeTransactions(List<Transaction> outcomeTransactions) {
        this.outcomeTransactions = outcomeTransactions;
    }

    public void setIncomeTransactions(List<Transaction> incomeTransactions) {
        this.incomeTransactions = incomeTransactions;
    }

    public void addIncomeTransaction (Transaction transaction) {
        incomeTransactions.add(transaction);
    }

    public void addOutcomeTransaction (Transaction transaction) {
        outcomeTransactions.add(transaction);
    }

    public Account getAccountByType(TYPE type){
        for (Account acc : accounts) {
            if (acc.getType() == type) {
                return acc;
            }
        }
        System.out.println("This client doesn't have " + type + " account");
        return null;
    }

    public void setUahBalance(Double uahBalance){
        for (Account acc : accounts) {
            if (acc.getType() == TYPE.UAH) {
                acc.setBalance(uahBalance);
            }
        }
        System.out.println("This client doesn't have " + TYPE.UAH + " account");
    }

    public void setUsdBalance(Double usdBalance){
        for (Account acc : accounts) {
            if (acc.getType() == TYPE.USD) {
                acc.setBalance(usdBalance);
            }
        }
        System.out.println("This client doesn't have " + TYPE.USD + " account");
    }
    public void setEurBalance(Double eurBalance){
        for (Account acc : accounts) {
            if (acc.getType() == TYPE.EUR) {
                acc.setBalance(eurBalance);
            }
        }
        System.out.println("This client doesn't have " + TYPE.EUR + " account");
    }

    public void addUahBalance(Double amount) {
        Account uahAcc = this.getAccountByType(TYPE.UAH);
        if (uahAcc != null) {
            uahAcc.addBalance(amount);
        } else System.out.println("This client doesn't have " + TYPE.UAH + " account");
    }

    public void addUsdBalance(Double amount) {
        Account usdAcc = this.getAccountByType(TYPE.USD);
        if (usdAcc != null) {
            usdAcc.addBalance(amount);
        } else System.out.println("This client doesn't have " + TYPE.USD + " account");
    }

    public void addEurBalance(Double amount) {
        Account eurAcc = this.getAccountByType(TYPE.EUR);
        if (eurAcc != null) {
            eurAcc.addBalance(amount);
        } else System.out.println("This client doesn't have " + TYPE.EUR + " account");
    }

    public void subtractUahBalance(Double amount) {
        Account uahAcc = this.getAccountByType(TYPE.UAH);
        if (uahAcc != null) {
            uahAcc.subtractBalance(amount);
        } else System.out.println("This client doesn't have " + TYPE.UAH + " account");
    }

    public void subtractUsdBalance(Double amount) {
        Account usdAcc = this.getAccountByType(TYPE.USD);
        if (usdAcc != null) {
            usdAcc.subtractBalance(amount);
        } else System.out.println("This client doesn't have " + TYPE.USD + " account");
    }

    public void subtractEurBalance(Double amount) {
        Account eurAcc = this.getAccountByType(TYPE.EUR);
        if (eurAcc != null) {
            eurAcc.subtractBalance(amount);
        } else System.out.println("This client doesn't have " + TYPE.EUR + " account");
    }

    public long getId() {
        return id;
    }

    public String getFull_name() {
        return fullName;
    }

    public void setFull_name(String full_name) {
        this.fullName = full_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        if (! accounts.contains(account) && ! hasTypeAccount(account)) {
            accounts.add(account);
        }
    }

    public boolean hasTypeAccount(Account account) {
        for (Account acc : accounts) {
            if (account.getType() == acc.getType()) {
                System.out.println("This client already have " + account.getType() + " account");
                return true;
            }
        }
        return false;
    }

    public String getSummaryBalance(double usdRate, double eurRate) {
        double uahBalance = this.getAccountByType(TYPE.UAH).getBalance();
        double usdBalance = this.getAccountByType(TYPE.USD).getBalance();
        double usdBalanceInUAH = usdBalance * usdRate;
        double eurBalance = this.getAccountByType(TYPE.EUR).getBalance();
        double eurBalanceInUAH = eurBalance * eurRate;
        double summaryBalanceInUAH = uahBalance + usdBalanceInUAH + eurBalanceInUAH;

        return "UAH balance: " + uahBalance + "\n" +
                "USD balance: " + usdBalance + " in UAH " + usdBalanceInUAH + "\n" +
                "EUR balance: " + eurBalance + " in UAH " + eurBalanceInUAH + "\n" +
                "Summary balance in UAH: " + summaryBalanceInUAH;
    }

    public void transferMoneyFromTo (double amount, TYPE from, TYPE to, double usdRate, double eurRate) {
        Account sendFrom = this.getAccountByType(from);
        Account sendTo = this.getAccountByType(to);
        if (from == TYPE.UAH && to == TYPE.USD) {
            if (sendFrom.subtractBalance(amount)) {
                sendTo.addBalance(amount/usdRate);
            }
        } else if (from == TYPE.UAH && to == TYPE.EUR) {
            if (sendFrom.subtractBalance(amount)) {
                sendTo.addBalance(amount/eurRate);
            }
        } else if (from == TYPE.USD && to == TYPE.UAH) {
            if (sendFrom.subtractBalance(amount)) {
                sendTo.addBalance(amount * usdRate);
            }
        } else if (from == TYPE.USD && to == TYPE.EUR) {
            if (sendFrom.subtractBalance(amount)) {
                sendTo.addBalance(amount * (usdRate / eurRate));
            }
        } else if (from == TYPE.EUR && to == TYPE.UAH) {
            if (sendFrom.subtractBalance(amount)) {
                sendTo.addBalance(amount * eurRate);
            }
        } else if (from == TYPE.EUR && to == TYPE.USD) {
            if (sendFrom.subtractBalance(amount)) {
                sendTo.addBalance(amount * (eurRate / usdRate));
            }
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "userId=" + id +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", accounts=" + accounts +
                ", incomeTransactions=" + incomeTransactions +
                ", outcomeTransactions=" + outcomeTransactions +
                '}';
    }
}
