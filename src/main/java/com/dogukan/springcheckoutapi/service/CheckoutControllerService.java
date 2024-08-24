package com.dogukan.springcheckoutapi.service;

import com.dogukan.springcheckoutapi.dto.AddItemDto;
import com.dogukan.springcheckoutapi.dto.AddVasItemDto;
import com.dogukan.springcheckoutapi.entity.Cart;

public interface CheckoutControllerService {
    void createItemThenAddToCart(AddItemDto itemDto);
    void createVasItemThenAddToItem(AddVasItemDto vasItemDto);
    Cart applyPromotionToCart();
    void deleteItemById(int itemId);
    void deleteAllItemsFromCart();
}
