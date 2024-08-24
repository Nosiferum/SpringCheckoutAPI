package com.dogukan.springcheckoutapi.builder;

import com.dogukan.springcheckoutapi.entity.Item;
import com.dogukan.springcheckoutapi.entity.VasItem;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private int id;
    private int categoryId;
    private int sellerId;
    private double price;
    private int quantity;
    private List<VasItem> vasItems;

    public ItemBuilder() {
        this.vasItems = new ArrayList<>();
    }

    public ItemBuilder id(int id) {
        this.id = id;
        return this;
    }

    public ItemBuilder categoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public ItemBuilder sellerId(int sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public ItemBuilder price(double price) {
        this.price = price;
        return this;
    }

    public ItemBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ItemBuilder addVasItem(VasItem vasItem) {
        this.vasItems.add(vasItem);
        return this;
    }

    public Item build() {
        Item item = new Item();
        item.setId(this.id);
        item.setCategoryId(this.categoryId);
        item.setSellerId(this.sellerId);
        item.setPrice(this.price);
        item.setQuantity(this.quantity);
        item.setVasItems(this.vasItems);
        return item;
    }
}