package com.dogukan.springcheckoutapi.builder;

import com.dogukan.springcheckoutapi.dto.AddItemDto;

public class AddItemDtoBuilder {
    private int itemId;
    private int categoryId;
    private int sellerId;
    private double price;
    private int quantity;

    public AddItemDtoBuilder itemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public AddItemDtoBuilder categoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public AddItemDtoBuilder sellerId(int sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public AddItemDtoBuilder price(double price) {
        this.price = price;
        return this;
    }

    public AddItemDtoBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public AddItemDto build() {
        AddItemDto addItemDto = new AddItemDto();
        addItemDto.setItemId(this.itemId);
        addItemDto.setCategoryId(this.categoryId);
        addItemDto.setSellerId(this.sellerId);
        addItemDto.setPrice(this.price);
        addItemDto.setQuantity(this.quantity);
        return addItemDto;
    }
}
