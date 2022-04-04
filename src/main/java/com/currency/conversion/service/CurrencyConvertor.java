package com.currency.conversion.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CurrencyConvertor {
    static Map<String, Map<String, Double>> currencyPairAdjacencyList = new HashMap<>();

    public CurrencyConvertor() {
        buildGraph();
    }

    public void buildGraph() {
        String data = "USD JPY BGN CZK DKK GBP HUF PLN RON SEK CHF ISK NOK HRK TRY AUD BRL CAD CNY HKD IDR ILS INR KRW MXN MYR NZD PHP SGD THB ZAR";
        String eurToCurrency = "1.1052 135.35 1.9558 24.376 7.4388 0.84145 368.12 4.6401 4.9452 10.3320 1.0217 142.00 9.6628 7.5675 16.2411 1.4696 5.2188 1.3805 7.0311 8.6596 15887.50 3.5315 83.9847 1345.61 21.9087 4.6534 1.5911 57.084 1.4985 36.941 16.1685";
        String[] cur = data.split(" ");
        String[] eurToCurrencyVal = eurToCurrency.split(" ");
        String base = "EUR";
        for (int i = 0; i < eurToCurrencyVal.length; i++) {
            String quote = cur[i].trim();
            String val = eurToCurrencyVal[i].trim();
            double ratio = Double.parseDouble(val);
            if (!currencyPairAdjacencyList.containsKey(base)) {
                currencyPairAdjacencyList.put(base, new HashMap<>());
            }
            currencyPairAdjacencyList.get(base).put(quote, ratio);
            if (!currencyPairAdjacencyList.containsKey(quote)) {
                currencyPairAdjacencyList.put(quote, new HashMap<>());
            }
            currencyPairAdjacencyList.get(quote).put(base, 1.0 / ratio);
        }
//        System.out.println(currencyPairAdjacencyList.size());
//        System.out.println(JsonUtils.parseNonProtoObj(currencyPairAdjacencyList));
    }

    public double getExchangeRate(String base, String quote) {
        base = base.toUpperCase();
        quote = quote.toUpperCase();
        Queue<String> q = new LinkedList<>();
        Queue<Double> val = new LinkedList<>();
        q.offer(base);
        val.offer(1.0);
        Set<String> visited = new HashSet<>();
        while (!q.isEmpty()) {
            String cur = q.poll();
            double num = val.poll();
            if (!visited.contains(cur)) {
                visited.add(cur);
                if (currencyPairAdjacencyList.containsKey(cur)) {
                    Map<String, Double> next = currencyPairAdjacencyList.get(cur);
                    for (String key : next.keySet()) {
                        if (!visited.contains(key)) {
                            q.offer(key);
                            if (key.equals(quote)) return num * next.get(key);
                            val.offer(num * next.get(key));
                        }
                    }
                }
            }
        }
        return -1;
    }

    public double getExchangeRate(String baseQuote) {
        String[] currencyPair = baseQuote.split("/");
        return getExchangeRate(currencyPair[0], currencyPair[1]);
    }

    public double convertCurrency(Double amount, String base, String quote) {
        return amount * getExchangeRate(base,quote);
    }
    public double convertCurrency(Double amount, String baseQuote) {
        String[] currencyPair = baseQuote.split("/");
        return amount *  getExchangeRate(currencyPair[0], currencyPair[1]);
    }

    public static void main(String[] args) {
        CurrencyConvertor currencyConvertor = new CurrencyConvertor();
        System.out.println(currencyConvertor.getExchangeRate("HUF", "USD"));
        System.out.println(currencyConvertor.getExchangeRate("EUR/USD"));
        System.out.println(currencyConvertor.convertCurrency(10.0,"EUR/USD"));
    }
}

class CurrencyPair {
    String base;
    String quote;
    double rate;

    public CurrencyPair(String base, String quote, double rate) {
        this.base = base;
        this.quote = quote;
        this.rate = rate;
    }
}
