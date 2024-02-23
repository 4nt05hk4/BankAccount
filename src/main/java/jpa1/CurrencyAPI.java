package jpa1;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CurrencyAPI {
    private static final String urlNBU = "https://bank.gov.ua/NBU_Exchange/exchange_site";
    public static Gson gson = new Gson();


    public static String buildRequestPeriod(Date dateStart, Date dateFinish, String currencyName) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        String result = urlNBU + "?start=" + formater.format(dateStart) +
                "&end=" + formater.format(dateFinish) +
                "&valcode=" + currencyName.toLowerCase() +
                "&sort=exchangedate&json";

        return result;
    }

    public static String buildRequestToday() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String result = urlNBU + "?start=" + formatter.format(date) +
                "&end=" + formatter.format(date) + "&sort=exchangedate&json";
        return result;
    }

    public static String getJsonFromAPI(String url) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {

                String jsonResponse = EntityUtils.toString(entity);

                return jsonResponse;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No json response");
        return null;
    }

    public static Currency[] getAllCurrencies() {
        return gson.fromJson(getJsonFromAPI(buildRequestToday()), Currency[].class);
    }

    public static Currency getCurrencyFromJSON(String jsonResponse) {

        return gson.fromJson(jsonResponse, Currency.class);
    }
}
