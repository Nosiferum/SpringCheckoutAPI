package com.dogukan.springcheckoutapi.controller;

import com.dogukan.springcheckoutapi.builder.CartBuilder;
import com.dogukan.springcheckoutapi.dto.AddItemDto;
import com.dogukan.springcheckoutapi.dto.AddVasItemDto;
import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.response.SuccessfulResponse;
import com.dogukan.springcheckoutapi.service.CheckoutControllerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {
    @Mock
    private CheckoutControllerService checkoutControllerService;

    @InjectMocks
    private CheckoutController checkoutController;

    private AddItemDto itemDto;
    private AddVasItemDto vasItemDto;
    private Cart cart;
    private final int ITEM_ID = 1;

    @BeforeEach
    void setup() {
        itemDto = new AddItemDto();
        vasItemDto = new AddVasItemDto();
        cart = new Cart();
    }

    @Test
    void test_CreateItemThenAddToCart_from_controller_should_call_createItemThenAddToCart_one_time() {
        // Given
        doNothing().when(checkoutControllerService).createItemThenAddToCart(itemDto);

        // When
        checkoutController.createItemThenAddToCart(itemDto);

        // Then
        verify(checkoutControllerService, times(1)).createItemThenAddToCart(itemDto);
    }

    @Test
    void test_CreateItemThenAddToCart_should_return_ok() {
        // Given
        doNothing().when(checkoutControllerService).createItemThenAddToCart(itemDto);

        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.createItemThenAddToCart(itemDto);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_CreateItemThenAddToCart_should_return_excepted_message() {
        // Given
        doNothing().when(checkoutControllerService).createItemThenAddToCart(itemDto);

        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.createItemThenAddToCart(itemDto);

        // Then
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "Item added successfully");
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_return_ok() {
        // Given
        doNothing().when(checkoutControllerService).createVasItemThenAddToItem(vasItemDto);

        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.createVasItemThenAddToItem(vasItemDto);

        // Then
        verify(checkoutControllerService, times(1)).createVasItemThenAddToItem(vasItemDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_call_CreateVasItemThenAddToItem_one_time() {
        // Given
        doNothing().when(checkoutControllerService).createVasItemThenAddToItem(vasItemDto);

        // When
        checkoutController.createVasItemThenAddToItem(vasItemDto);

        // Then
        verify(checkoutControllerService, times(1)).createVasItemThenAddToItem(vasItemDto);
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_return_expected_message() {
        // Given
        doNothing().when(checkoutControllerService).createVasItemThenAddToItem(vasItemDto);

        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.createVasItemThenAddToItem(vasItemDto);

        // Then
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "Vas item added successfully");
    }

    @Test
    void test_DisplayCart_should_call_applyPromotionToCart_one_time() {
        // Given
        when(checkoutControllerService.applyPromotionToCart()).thenReturn(cart);

        // When
        checkoutController.displayCart();

        // Then
        verify(checkoutControllerService, times(1)).applyPromotionToCart();
    }

    @Test
    void test_DisplayCart_should_return_ok() {
        // Given
        when(checkoutControllerService.applyPromotionToCart()).thenReturn(cart);

        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.displayCart();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_DisplayCart_should_return_expected_message() {
        // Given
        cart = new CartBuilder()
                .appliedPromotionId(1232)
                .totalDiscount(250)
                .build();
        when(checkoutControllerService.applyPromotionToCart()).thenReturn(cart);

        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.displayCart();

        // Then
        verify(checkoutControllerService, times(1)).applyPromotionToCart();
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "Cart{items=[], totalPrice=0.0, appliedPromotionId=1232, totalDiscount=250.0}");
    }

    @Test
    void test_DeleteItem_should_call_deleteItemById_one_time() {
        // When
        checkoutController.deleteItem(ITEM_ID);

        // Then
        verify(checkoutControllerService, times(1)).deleteItemById(ITEM_ID);
    }

    @Test
    void test_DeleteItem_should_return_ok() {
        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.deleteItem(ITEM_ID);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_DeleteItem_should_return_expected_message() {
        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.deleteItem(ITEM_ID);

        // Then
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getMessage(), "Item removed successfully");
    }

    @Test
    void test_ResetCart_should_call_deleteAllItemsFromCart_one_time() {
        // When
        checkoutController.resetCart();

        // Then
        verify(checkoutControllerService, times(1)).deleteAllItemsFromCart();
    }

    @Test
    void test_ResetCart_should_return_ok() {
        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.resetCart();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_ResetCart_should_return_expected_message() {
        // When
        ResponseEntity<SuccessfulResponse> responseEntity = checkoutController.resetCart();

        // Then
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getMessage(), "Cart reset successfully");
    }
}