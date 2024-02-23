package jpa1;

import java.time.ZonedDateTime;
import java.util.Date;

public class App {
    public static void main(String[] args) {

        CurrencyDAO currencyDAO = new CurrencyDAO();
        ClientDAO clientDAO = new ClientDAO();

        currencyDAO.saveCurrencyArray(CurrencyAPI.getAllCurrencies());
        double usdRate = currencyDAO.getCurrencyByName("usd").getValue();
        double eurRate = currencyDAO.getCurrencyByName("eur").getValue();

        Client client = clientDAO.createAndSaveClient("Anton", 23);

        clientDAO.topUpAccount(230.00, TYPE.USD, client);
        clientDAO.topUpAccount(170.00, TYPE.EUR, client);
        clientDAO.topUpAccount(5000.00, TYPE.UAH, client);

        System.out.println(client.getAccountByType(TYPE.USD).getBalance());
        System.out.println(client.getAccountByType(TYPE.EUR).getBalance());
        System.out.println(client.getAccountByType(TYPE.UAH).getBalance());
        System.out.println("USD rate: " + usdRate);
        System.out.println("EUR rate: " + eurRate);
        System.out.println("\n" + client.getSummaryBalance(usdRate, eurRate));

        client.transferMoneyFromTo(3000, TYPE.UAH, TYPE.USD, usdRate,eurRate);
        System.out.println("\n" + client.getSummaryBalance(usdRate, eurRate));

        client.transferMoneyFromTo(300, TYPE.USD, TYPE.EUR, usdRate,eurRate);
        System.out.println("\n" + client.getSummaryBalance(usdRate, eurRate));

        client.transferMoneyFromTo(200, TYPE.EUR, TYPE.USD, usdRate,eurRate);
        System.out.println("\n" + client.getSummaryBalance(usdRate, eurRate));

        client.transferMoneyFromTo(200, TYPE.USD, TYPE.UAH, usdRate,eurRate);
        System.out.println("\n" + client.getSummaryBalance(usdRate, eurRate));
    }
}


