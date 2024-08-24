package com.dogukan.springcheckoutapi.promotion;

import com.dogukan.springcheckoutapi.entity.Cart;

public interface Promotion {
    double applyDiscount(Cart cart, double totalPrice);
    int getId();
}
