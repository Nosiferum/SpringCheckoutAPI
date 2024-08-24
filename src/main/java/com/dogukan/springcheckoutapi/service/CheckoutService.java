package com.dogukan.springcheckoutapi.service;

import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.entity.Item;
import com.dogukan.springcheckoutapi.entity.VasItem;

public interface CheckoutService {
    void save(Cart cart);
    Cart findCartById(int id);
    void deleteCartById(int id);
    void save(Item item);
    Item findItemById(int id);
    boolean isItemExist(int id);
    boolean isDigitalItemExist(int digitalItemCategoryId);
    boolean isOtherThanDigitalItemExist(int digitalItemCategoryId);
    void deleteItemById(int id);
    void deleteItem(Item item);
    void save(VasItem vasItem);
    VasItem findVasItemById(int id);
    boolean isVasItemExist(int id);
    void deleteVasItemById(int id);
}
