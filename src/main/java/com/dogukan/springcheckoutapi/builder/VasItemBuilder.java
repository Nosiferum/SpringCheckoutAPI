package com.dogukan.springcheckoutapi.builder;

import com.dogukan.springcheckoutapi.entity.VasItem;

public class VasItemBuilder {
    private int id;
    private int categoryId;
    private int sellerId;
    private double price;
    private int quantity;

    public VasItemBuilder id(int id) {
        this.id = id;
        return this;
    }

    public VasItemBuilder categoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public VasItemBuilder sellerId(int sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public VasItemBuilder price(double price) {
        this.price = price;
        return this;
    }

    public VasItemBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public VasItem build() {
        VasItem vasItem = new VasItem();
        vasItem.setId(this.id);
        vasItem.setCategoryId(this.categoryId);
        vasItem.setSellerId(this.sellerId);
        vasItem.setPrice(this.price);
        vasItem.setQuantity(this.quantity);
        return vasItem;
    }
}
