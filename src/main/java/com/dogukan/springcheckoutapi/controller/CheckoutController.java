package com.dogukan.springcheckoutapi.controller;

import com.dogukan.springcheckoutapi.dto.AddItemDto;
import com.dogukan.springcheckoutapi.dto.AddVasItemDto;
import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.response.SuccessfulResponse;
import com.dogukan.springcheckoutapi.service.CheckoutControllerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutControllerService checkoutControllerService;

    @Autowired
    public CheckoutController(CheckoutControllerService checkoutControllerService) {
        this.checkoutControllerService = checkoutControllerService;
    }

    @PostMapping("/item")
    public ResponseEntity<SuccessfulResponse> createItemThenAddToCart(@Valid @RequestBody AddItemDto itemDto) {
        checkoutControllerService.createItemThenAddToCart(itemDto);

        return ResponseEntity.ok(new SuccessfulResponse(true, "Item added successfully"));
    }

    @PostMapping("/vas-item")
    public ResponseEntity<SuccessfulResponse> createVasItemThenAddToItem(@Valid @RequestBody AddVasItemDto vasItemDto) {
        checkoutControllerService.createVasItemThenAddToItem(vasItemDto);

        return ResponseEntity.ok(new SuccessfulResponse(true, "Vas item added successfully"));
    }

    @GetMapping("/cart")
    public ResponseEntity<SuccessfulResponse> displayCart() {
        Cart cart = checkoutControllerService.applyPromotionToCart();

        return ResponseEntity.ok(new SuccessfulResponse(true, cart.toString()));
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<SuccessfulResponse> deleteItem(@PathVariable(name = "itemId") int itemId) {
        checkoutControllerService.deleteItemById(itemId);

        return ResponseEntity.ok(new SuccessfulResponse(true, "Item removed successfully"));
    }

    @DeleteMapping("/cart")
    public ResponseEntity<SuccessfulResponse> resetCart() {
        checkoutControllerService.deleteAllItemsFromCart();

        return ResponseEntity.ok(new SuccessfulResponse(true, "Cart reset successfully"));
    }
}
