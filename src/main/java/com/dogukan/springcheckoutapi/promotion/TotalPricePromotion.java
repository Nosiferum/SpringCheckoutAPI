package com.dogukan.springcheckoutapi.promotion;

import com.dogukan.springcheckoutapi.entity.Cart;

import java.util.HashMap;
import java.util.Map;

public class TotalPricePromotion implements Promotion {

    private final int id = 1232;
    private final double DISCOUNT_THRESHOLD_1 = 250.0;
    private final double DISCOUNT_THRESHOLD_2 = 500.0;
    private final double DISCOUNT_THRESHOLD_3 = 1000.0;
    private final Map<Double, Double> discountMap = new HashMap<>();

    public TotalPricePromotion() {
        discountMap.put(5000.0, DISCOUNT_THRESHOLD_1);
        discountMap.put(10000.0, DISCOUNT_THRESHOLD_2);
        discountMap.put(50000.0, DISCOUNT_THRESHOLD_3);
    }

    @Override
    public double applyDiscount(Cart cart, double totalPrice) {
        double discountPrice = discountMap.entrySet().stream()
                .filter(entry -> totalPrice < entry.getKey())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(2000.0);

        return totalPrice - discountPrice;
    }

    public int getId() {
        return id;
    }
}
