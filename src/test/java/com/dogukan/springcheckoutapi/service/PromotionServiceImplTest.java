package com.dogukan.springcheckoutapi.service;

import com.dogukan.springcheckoutapi.builder.CartBuilder;
import com.dogukan.springcheckoutapi.builder.ItemBuilder;
import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.promotion.CategoryPromotion;
import com.dogukan.springcheckoutapi.promotion.Promotion;
import com.dogukan.springcheckoutapi.promotion.SameSellerPromotion;
import com.dogukan.springcheckoutapi.promotion.TotalPricePromotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromotionServiceImplTest {
    @Mock
    private Promotion promotion;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    private Cart cart;

    @BeforeEach
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void test_setPromotion_should_set_promotion() {
        // When
        promotionService.setPromotion(promotion);

        // Then
        assertEquals(promotion, promotionService.getPromotion());
    }

    @Test
    public void test_applyPromotion_should_call_applyDiscount_one_time() {
        // Given
        cart = new CartBuilder()
                .totalPrice(1000)
                .build();
        double discountAmount = 10.0;
        when(promotion.applyDiscount(cart, cart.getTotalPrice())).thenReturn(discountAmount);

        // When
        promotionService.applyPromotion(cart, cart.getTotalPrice());

        // Then
        verify(promotion, times(1)).applyDiscount(cart, cart.getTotalPrice());
    }

    @Test
    public void test_applyPromotion_should_apply_same_seller_promotion() {
        // Given
        cart = new CartBuilder()
                .addItem(new ItemBuilder()
                        .sellerId(1)
                        .build())
                .addItem(new ItemBuilder()
                        .sellerId(1)
                        .build())
                .totalPrice(1000.0)
                .build();
        double discountedAmount = 900.0;
        promotionService.setPromotion(new SameSellerPromotion());

        // When
        double result = promotionService.applyPromotion(cart, cart.getTotalPrice());

        // Then
        assertEquals(discountedAmount, result, 0.001);
    }

    @Test
    public void test_applyPromotion_should_apply_category_promotion() {
        // Given
        cart = new CartBuilder()
                .addItem(new ItemBuilder()
                        .categoryId(3003)
                        .build())
                .totalPrice(1000.0)
                .build();
        double discountedAmount = 950.0;
        promotionService.setPromotion(new CategoryPromotion());

        // When
        double result = promotionService.applyPromotion(cart, cart.getTotalPrice());

        // Then
        assertEquals(discountedAmount, result, 0.001);
    }

    @Test
    public void test_applyPromotion_should_apply_total_price_promotion_250_discount() {
        // Given
        cart = new CartBuilder()
                .totalPrice(1000.0)
                .build();
        double discountedAmount = 750.0;
        promotionService.setPromotion(new TotalPricePromotion());

        // When
        double result = promotionService.applyPromotion(cart, cart.getTotalPrice());

        // Then
        assertEquals(discountedAmount, result, 0.001);
    }

    @Test
    public void test_applyPromotion_should_apply_total_price_promotion_500_discount() {
        // Given
        cart = new CartBuilder()
                .totalPrice(5000.0)
                .build();
        double discountedAmount = 4500.0;
        promotionService.setPromotion(new TotalPricePromotion());

        // When
        double result = promotionService.applyPromotion(cart, cart.getTotalPrice());

        // Then
        assertEquals(discountedAmount, result, 0.001);
    }

    @Test
    public void test_applyPromotion_should_apply_total_price_promotion_1000_discount() {
        // Given
        cart = new CartBuilder()
                .totalPrice(20000.0)
                .build();
        double discountedAmount = 19000.0;
        promotionService.setPromotion(new TotalPricePromotion());

        // When
        double result = promotionService.applyPromotion(cart, cart.getTotalPrice());

        // Then
        assertEquals(discountedAmount, result, 0.001);
    }

    @Test
    public void test_applyPromotion_should_apply_total_price_promotion_2000_discount() {
        // Given
        cart = new CartBuilder()
                .totalPrice(60000.0)
                .build();
        double discountedAmount = 58000.0;
        promotionService.setPromotion(new TotalPricePromotion());

        // When
        double result = promotionService.applyPromotion(cart, cart.getTotalPrice());

        // Then
        assertEquals(discountedAmount, result, 0.001);
    }
}