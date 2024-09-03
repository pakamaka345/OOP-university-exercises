package com.umcs.shop;

import com.umcs.auth.Account;

import java.util.List;

public class ShopClient extends Account {
    private List<Cart> carts;
    public ShopClient(String username, int id, List<Cart> carts) {
        super(username, id);
        this.carts = carts;
    }

    public List<Cart> getCarts() {
        return carts;
    }
}
