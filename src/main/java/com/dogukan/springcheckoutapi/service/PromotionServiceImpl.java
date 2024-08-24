package com.dogukan.springcheckoutapi.service;

import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.promotion.Promotion;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService {

    private Promotion promotion;

    @Override
    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public double applyPromotion(Cart cart, double totalPrice) {
        return promotion.applyDiscount(cart, totalPrice);
    }
}