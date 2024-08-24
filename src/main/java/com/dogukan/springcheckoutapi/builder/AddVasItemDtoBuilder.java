package com.dogukan.springcheckoutapi.builder;

import com.dogukan.springcheckoutapi.dto.AddVasItemDto;

public class AddVasItemDtoBuilder {
    private int itemId;
    private int vasItemId;
    private int categoryId;
    private int sellerId;
    private double price;
    private int quantity;

    public AddVasItemDtoBuilder vasItemId(int vasItemId) {
        this.vasItemId = vasItemId;
        return this;
    }

    public AddVasItemDtoBuilder itemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public AddVasItemDtoBuilder categoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public AddVasItemDtoBuilder sellerId(int sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public AddVasItemDtoBuilder price(double price) {
        this.price = price;
        return this;
    }

    public AddVasItemDtoBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public AddVasItemDto build() {
        AddVasItemDto addVasItemDto = new AddVasItemDto();
        addVasItemDto.setItemId(this.itemId);
        addVasItemDto.setCategoryId(this.categoryId);
        addVasItemDto.setSellerId(this.sellerId);
        addVasItemDto.setPrice(this.price);
        addVasItemDto.setQuantity(this.quantity);
        addVasItemDto.setVasItemId(this.vasItemId);
        return addVasItemDto;
    }
}
