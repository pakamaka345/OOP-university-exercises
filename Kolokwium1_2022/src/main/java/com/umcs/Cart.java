package com.umcs;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> cart = new HashMap<>();
    public void addProduct(Product product, int amount){
        cart.put(product, cart.getOrDefault(product, 0) + amount);
    }
    public double getPrice(int year, int month){
        return cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice(year, month) * entry.getValue())
                .sum();
    }
    public double getInflation(int year1, int month1, int year2, int month2){
        int months = (year2 - year1) * 12 + (month2 - month1);
        double price1 = getPrice(year1, month1);
        double price2 = getPrice(year2, month2);
        return (price2 - price1) / price1 * 100 / months * 12;
    }
}
