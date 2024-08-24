package com.dogukan.springcheckoutapi.service;

import com.dogukan.springcheckoutapi.dto.AddItemDto;
import com.dogukan.springcheckoutapi.dto.AddVasItemDto;
import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.entity.Item;
import com.dogukan.springcheckoutapi.entity.VasItem;
import com.dogukan.springcheckoutapi.error.BadRequestException;
import com.dogukan.springcheckoutapi.promotion.CategoryPromotion;
import com.dogukan.springcheckoutapi.promotion.SameSellerPromotion;
import com.dogukan.springcheckoutapi.promotion.TotalPricePromotion;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CheckoutControllerServiceImpl implements CheckoutControllerService {
    private final int PERSISTENT_CART_ID = 1;
    private final int MAXIMUM_ITEM_IN_CART = 30;
    private final int MAXIMUM_UNIQUE_ITEM_IN_CART = 10;
    private final int MAXIMUM_ITEM_QUANTITY = 10;
    private final int DIGITAL_ITEM_CATEGORY_ID = 7889;
    private final int MAXIMUM_DIGITAL_ITEM_QUANTITY = 5;
    private final int MAXIMUM_VAS_ITEM_IN_ITEM = 3;
    private final int FURNITURE_CATEGORY_ID = 1001;
    private final int ELECTRONICS_CATEGORY_ID = 3004;
    private final int VAS_ITEM_CATEGORY_ID = 3242;
    private static int VAS_ITEM_SELLER_ID = 5003;
    private final double MAXIMUM_TOTAL_PRICE_IN_CART = 500000.0;
    private final CheckoutService checkoutService;
    private final PromotionService promotionService;

    public CheckoutControllerServiceImpl(CheckoutService checkoutService, PromotionService promotionService) {
        this.checkoutService = checkoutService;
        this.promotionService = promotionService;
    }

    @Override
    public void createItemThenAddToCart(AddItemDto itemDto) {
        Cart cart = checkoutService.findCartById(PERSISTENT_CART_ID);
        validateItem(itemDto, cart);

        cart.getItems().add(
                new Item(
                        itemDto.getItemId(),
                        itemDto.getCategoryId(),
                        itemDto.getSellerId(),
                        itemDto.getPrice(),
                        itemDto.getQuantity()));

        checkoutService.save(cart);
    }

    @Override
    public void createVasItemThenAddToItem(AddVasItemDto vasItemDto) {
        Cart cart = checkoutService.findCartById(PERSISTENT_CART_ID);
        Item item = checkoutService.findItemById(vasItemDto.getItemId());
        validateVasItem(vasItemDto, item, cart);

        item.getVasItems().add(
                new VasItem(
                        vasItemDto.getVasItemId(),
                        vasItemDto.getCategoryId(),
                        vasItemDto.getSellerId(),
                        vasItemDto.getPrice(),
                        vasItemDto.getQuantity()));

        checkoutService.save(item);
    }

    @Override
    public Cart applyPromotionToCart() {
        Cart cart = checkoutService.findCartById(PERSISTENT_CART_ID);

        double totalPrice = getTotalPrice(cart);
        Map<Integer, Double> discountedPrices = new HashMap<>();

        promotionService.setPromotion(new CategoryPromotion());
        discountedPrices.put(promotionService.getPromotion().getId(),
                promotionService.applyPromotion(cart, totalPrice));

        promotionService.setPromotion(new SameSellerPromotion());
        discountedPrices.put(promotionService.getPromotion().getId(),
                promotionService.applyPromotion(cart, totalPrice));

        promotionService.setPromotion(new TotalPricePromotion());
        discountedPrices.put(promotionService.getPromotion().getId(),
                promotionService.applyPromotion(cart, totalPrice));

        Map.Entry<Integer, Double> minimumEntry = discountedPrices.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .orElseThrow(NoSuchElementException::new);

        cart.setTotalPrice(totalPrice);
        cart.setAppliedPromotionId(minimumEntry.getKey());
        cart.setTotalDiscount(totalPrice - minimumEntry.getValue());

        return cart;
    }

    @Override
    public void deleteItemById(int itemId) {
        if (!checkoutService.isItemExist(itemId))
            throw new BadRequestException("The item does not exists");

        checkoutService.deleteItemById(itemId);
    }

    @Override
    public void deleteAllItemsFromCart() {
        Cart cart = checkoutService.findCartById(PERSISTENT_CART_ID);

        cart.getItems().forEach(checkoutService::deleteItem);
    }

    private void validateItem(AddItemDto itemDto, Cart cart) {
        if (checkoutService.isItemExist(itemDto.getItemId()))
            throw new BadRequestException("The item already exists");

        if (cart.getItems().size() >= MAXIMUM_UNIQUE_ITEM_IN_CART)
            throw new BadRequestException("The total number of unique items in a cart cannot exceed the number: "
                    + MAXIMUM_UNIQUE_ITEM_IN_CART);

        if (itemDto.getQuantity() > MAXIMUM_ITEM_QUANTITY)
            throw new BadRequestException("The total quantity of an item cannot exceed the number: "
                    + MAXIMUM_ITEM_IN_CART);

        int totalItemCount = getTotalItemCount(itemDto.getQuantity(), cart);

        if (totalItemCount > MAXIMUM_ITEM_IN_CART)
            throw new BadRequestException("The total number of items in a cart cannot exceed the number: "
                    + MAXIMUM_ITEM_IN_CART);

        if (checkoutService.isDigitalItemExist(DIGITAL_ITEM_CATEGORY_ID) &&
                itemDto.getCategoryId() != DIGITAL_ITEM_CATEGORY_ID)
            throw new BadRequestException("You cannot add a physical item to a digital cart");

        if (itemDto.getCategoryId() == DIGITAL_ITEM_CATEGORY_ID &&
                itemDto.getQuantity() > MAXIMUM_DIGITAL_ITEM_QUANTITY)
            throw new BadRequestException("The total quantity of a digital item cannot exceed the number: "
                    + MAXIMUM_DIGITAL_ITEM_QUANTITY);

        if (checkoutService.isOtherThanDigitalItemExist(DIGITAL_ITEM_CATEGORY_ID) &&
                itemDto.getCategoryId() == DIGITAL_ITEM_CATEGORY_ID)
            throw new BadRequestException("You cannot add a digital item to a physical cart");

        double totalPrice = getTotalPrice(cart);
        totalPrice += itemDto.getPrice();

        if (totalPrice > MAXIMUM_TOTAL_PRICE_IN_CART)
            throw new BadRequestException("The total price of cart cannot exceed the value: "
                    + MAXIMUM_TOTAL_PRICE_IN_CART);
    }

    private void validateVasItem(AddVasItemDto vasItemDto, Item item, Cart cart) {
        if (checkoutService.isVasItemExist(vasItemDto.getVasItemId()))
            throw new BadRequestException("The vas item already exists");

        if (vasItemDto.getPrice() > item.getPrice())
            throw new BadRequestException("The price of a vas item cannot exceed the price of an item");

        if (item.getCategoryId() != FURNITURE_CATEGORY_ID && item.getCategoryId() != ELECTRONICS_CATEGORY_ID)
            throw new BadRequestException("The vas item must be added to an item with the category ID of: " +
                    FURNITURE_CATEGORY_ID + " or " + ELECTRONICS_CATEGORY_ID);

        if (vasItemDto.getCategoryId() != VAS_ITEM_CATEGORY_ID)
            throw new BadRequestException("The vas item must be created with the category ID of: " +
                    VAS_ITEM_CATEGORY_ID);

        if (vasItemDto.getSellerId() != VAS_ITEM_SELLER_ID)
            throw new BadRequestException("The vas item must be created with the seller ID of: " +
                    VAS_ITEM_SELLER_ID);

        int totalItemCount = getTotalItemCount(vasItemDto.getQuantity(), cart);

        if (totalItemCount > MAXIMUM_ITEM_IN_CART)
            throw new BadRequestException("The total number of items in a cart cannot exceed the number: "
                    + MAXIMUM_ITEM_IN_CART);

        if (item.getVasItems().size() >= MAXIMUM_VAS_ITEM_IN_ITEM)
            throw new BadRequestException("The total number of unique vas items added to an item " +
                    "cannot exceed the number: "
                    + MAXIMUM_VAS_ITEM_IN_ITEM);

        double totalPrice = getTotalPrice(cart);
        totalPrice += vasItemDto.getPrice();

        if (totalPrice > MAXIMUM_TOTAL_PRICE_IN_CART)
            throw new BadRequestException("The total price of cart cannot exceed the value: "
                    + MAXIMUM_TOTAL_PRICE_IN_CART);
    }

    private double getTotalPrice(Cart cart) {
        double totalItemPrice = cart.getItems()
                .stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();

        double totalVasItemPrice = cart.getItems().stream()
                .flatMap(item -> item.getVasItems().stream())
                .mapToDouble(vasItem -> vasItem.getQuantity() * vasItem.getPrice())
                .sum();

        return totalItemPrice + totalVasItemPrice;
    }

    private int getTotalItemCount(int quantity, Cart cart) {
        int itemCount = cart.getItems()
                .stream()
                .mapToInt(Item::getQuantity)
                .sum();

        int vasItemCount = cart.getItems().stream()
                .flatMap(item -> item.getVasItems().stream())
                .mapToInt(VasItem::getQuantity)
                .sum();

        return itemCount + vasItemCount + quantity;
    }
}
