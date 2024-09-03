package com.umcs.shop;

import com.umcs.auth.Account;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int id;
    private Account account;
    private Map<Integer, Product> productList;

    public Cart(int id, int userId, String username){
        this.id = id;
        this.account = new Account(username, userId);
        productList = new HashMap<>();
    }
    public void addProduct(Product product, int quantity){
        int productId = product.id();
        for (int i = 0; i < quantity; i++){
            productList.put(productId, product);
        }
    }
    public double price(){
        double price = 0;
        for (Product product : productList.values()){
            price += product.price();
        }
        return price;
    }
}
