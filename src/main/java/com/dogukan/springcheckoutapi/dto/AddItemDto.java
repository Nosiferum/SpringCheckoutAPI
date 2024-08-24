package com.dogukan.springcheckoutapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddItemDto {
    @NotNull(message = "item id cannot be null")
    @Positive(message = "item id must be positive")
    protected int itemId;

    @NotNull(message = "category id cannot be null")
    @Positive(message = "category id must be positive")
    protected int categoryId;

    @NotNull(message = "seller id cannot be null")
    @Positive(message = "seller id must be positive")
    protected int sellerId;

    @NotNull(message = "price cannot be null")
    @Positive(message = "price must be positive")
    protected double price;

    @NotNull(message = "quantity cannot be null")
    @Positive(message = "quantity must be positive")
    protected int quantity;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
