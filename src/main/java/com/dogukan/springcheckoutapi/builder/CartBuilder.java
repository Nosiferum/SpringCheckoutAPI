package com.dogukan.springcheckoutapi.builder;

import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class CartBuilder {
    private double totalPrice;
    private int appliedPromotionId;
    private double totalDiscount;
    private List<Item> items;

    public CartBuilder() {
        this.items = new ArrayList<>();
    }

    public CartBuilder totalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public CartBuilder appliedPromotionId(int appliedPromotionId) {
        this.appliedPromotionId = appliedPromotionId;
        return this;
    }

    public CartBuilder totalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
        return this;
    }

    public CartBuilder addItem(Item item) {
        this.items.add(item);
        return this;
    }

    public Cart build() {
        Cart cart = new Cart();
        cart.setTotalPrice(this.totalPrice);
        cart.setAppliedPromotionId(this.appliedPromotionId);
        cart.setTotalDiscount(this.totalDiscount);
        cart.setItems(this.items);
        return cart;
    }
}
