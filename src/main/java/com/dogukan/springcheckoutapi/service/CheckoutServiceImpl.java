package com.dogukan.springcheckoutapi.service;

import com.dogukan.springcheckoutapi.entity.*;
import com.dogukan.springcheckoutapi.error.CheckoutEntityNotFoundException;
import com.dogukan.springcheckoutapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final VasItemRepository vasItemRepository;

    @Autowired
    public CheckoutServiceImpl(CartRepository cartRepository, ItemRepository itemRepository,
                           VasItemRepository vasItemRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.vasItemRepository = vasItemRepository;
    }

    @Transactional
    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart findCartById(int id) {
        return cartRepository.findById(id)
                .orElseThrow(() ->
                        new CheckoutEntityNotFoundException("Cart not found with id: " + id));
    }

    @Transactional
    @Override
    public void deleteCartById(int id) {
        cartRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item findItemById(int id) {
        return itemRepository.findById(id)
                .orElseThrow(() ->
                        new CheckoutEntityNotFoundException("Item not found with id: " + id));
    }

    @Override
    public boolean isItemExist(int id) {
        return itemRepository.findById(id).isPresent();
    }

    @Override
    public boolean isDigitalItemExist(int digitalItemCategoryId) {
        return !itemRepository.findByCategoryId(digitalItemCategoryId).isEmpty();
    }

    @Override
    public boolean isOtherThanDigitalItemExist(int digitalItemCategoryId) {
        return itemRepository.isExistsByCategoryIdOtherThan(digitalItemCategoryId);
    }

    @Transactional
    @Override
    public void deleteItemById(int id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    @Transactional
    @Override
    public void save(VasItem vasItem) {
        vasItemRepository.save(vasItem);
    }

    @Override
    public VasItem findVasItemById(int id) {
        return vasItemRepository.findById(id)
                .orElseThrow(() ->
                        new CheckoutEntityNotFoundException("Vas Item not found with id: " + id));
    }

    @Override
    public boolean isVasItemExist(int id) {
        return vasItemRepository.findById(id).isPresent();
    }

    @Transactional
    @Override
    public void deleteVasItemById(int id) {
        vasItemRepository.deleteById(id);
    }
}
