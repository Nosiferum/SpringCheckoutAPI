package com.dogukan.springcheckoutapi.promotion;

import com.dogukan.springcheckoutapi.entity.Cart;

public class CategoryPromotion implements Promotion {

    private final int id = 5676;
    private final  int PROMOTION_CATEGORY = 3003;

    @Override
    public double applyDiscount(Cart cart, double totalPrice) {
        boolean isFoundCategory = cart.getItems()
                .stream()
                .anyMatch(item -> item.getCategoryId() == PROMOTION_CATEGORY);

        if (!isFoundCategory) return totalPrice;

        return totalPrice * 0.95;
    }

    public int getId() {
        return id;
    }
}
