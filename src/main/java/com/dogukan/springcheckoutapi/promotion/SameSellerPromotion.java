package com.dogukan.springcheckoutapi.promotion;

import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.entity.Item;

public class SameSellerPromotion implements Promotion {

    private final int id = 9909;

    @Override
    public double applyDiscount(Cart cart, double totalPrice) {
        boolean areAllItemsHaveSameSeller = cart.getItems()
                .stream()
                .map(Item::getSellerId)
                .distinct()
                .count() == 1;

        if (!areAllItemsHaveSameSeller) return totalPrice;

        return totalPrice * 0.9;
    }

    public int getId() {
        return id;
    }
}
