package com.dogukan.springcheckoutapi.service;

import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.promotion.Promotion;
import org.springframework.stereotype.Service;

@Service
public interface PromotionService {
    void setPromotion(Promotion promotion);
    Promotion getPromotion();
    double applyPromotion(Cart cart, double totalPrice);
}
