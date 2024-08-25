package com.dogukan.springcheckoutapi.service;

import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.entity.Item;
import com.dogukan.springcheckoutapi.entity.VasItem;
import com.dogukan.springcheckoutapi.error.CheckoutEntityNotFoundException;
import com.dogukan.springcheckoutapi.repository.CartRepository;
import com.dogukan.springcheckoutapi.repository.ItemRepository;
import com.dogukan.springcheckoutapi.repository.VasItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private VasItemRepository vasItemRepository;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private Cart cart;
    private Item item;
    private VasItem vasItem;
    List<Item> digitalItems;
    private final int VAS_ITEM_ID = 1;
    private final int CART_ID = 1;
    private final int ITEM_ID = 1;


    @BeforeEach
    void setup() {
        cart = new Cart();
        item = new Item();
        vasItem = new VasItem();
        digitalItems = List.of(new Item());
    }

    @Test
    public void test_saveCart_should_call_save_one_time() {
        // When
        checkoutService.save(cart);

        // Then
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void test_findCartById_should_return_cart_when_cart_exists() {
        // Given
        when(cartRepository.findById(anyInt())).thenReturn(Optional.of(cart));

        // When
        Cart foundCart = checkoutService.findCartById(anyInt());

        // Then
        assertEquals(cart, foundCart);
    }

    @Test
    public void test_findCartById_should_throw_CheckoutEntityNotFoundException_when_cart_does_not_exist() {
        // Given
        when(cartRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When and Then
        assertThrows(CheckoutEntityNotFoundException.class, () ->
                checkoutService.findCartById(anyInt()));
    }

    @Test
    public void test_findCartById_should_throw_CheckoutEntityNotFoundException_with_expected_message_when_cart_does_not_exist() {
        // Given
        when(cartRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When
        CheckoutEntityNotFoundException checkoutEntityNotFoundException =
                assertThrows(CheckoutEntityNotFoundException.class, () ->
                        checkoutService.findCartById(anyInt()));

        // Then
        assertEquals(checkoutEntityNotFoundException.getMessage(),
                "Cart not found with id: " + cart.getId());
    }

    @Test
    public void test_deleteCartById_should_call_deleteById_one_time() {
        // When
        checkoutService.deleteCartById(CART_ID);

        // Then
        verify(cartRepository, times(1)).deleteById(CART_ID);
    }

    @Test
    public void test_saveItem_should_call_save_one_time() {
        // When
        checkoutService.save(item);

        // Then
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    public void test_deleteItem_should_call_delete_one_time() {
        // When
        checkoutService.deleteItem(item);

        // Then
        verify(itemRepository, times(1)).delete(item);
    }

    @Test
    public void test_findItemById_should_return_item_when_item_exists() {
        // Given
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        // When
        Item foundItem = checkoutService.findItemById(anyInt());

        // Then
        assertEquals(item, foundItem);
    }

    @Test
    public void test_findItemById_should_throw_CheckoutEntityNotFoundException_when_item_does_not_exist() {
        // Given
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When and Then
        assertThrows(CheckoutEntityNotFoundException.class, () ->
                checkoutService.findItemById(anyInt()));
    }

    @Test
    public void test_findItemById_should_throw_CheckoutEntityNotFoundException_with_expected_message_when_item_does_not_exist() {
        // Given
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When
        CheckoutEntityNotFoundException checkoutEntityNotFoundException =
                assertThrows(CheckoutEntityNotFoundException.class, () ->
                        checkoutService.findItemById(anyInt()));

        // Then
        assertEquals(checkoutEntityNotFoundException.getMessage(),
                "Item not found with id: " + item.getId());
    }

    @Test
    public void test_isItemExist_should_call_findById_one_time() {
        // Given
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        // When
        checkoutService.isItemExist(anyInt());

        // Then
        verify(itemRepository, times(1)).findById(anyInt());
    }

    @Test
    public void test_isItemExist_when_item_exists_should_return_true() {
        // Given
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        // When
        boolean result = checkoutService.isItemExist(anyInt());

        // Then
        assertTrue(result);
    }

    @Test
    public void test_isItemExist_when_item_does_not_exist_should_return_false() {
        // Given
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When
        boolean result = checkoutService.isItemExist(anyInt());

        // Then
        assertFalse(result);
    }

    @Test
    public void test_deleteItemById_should_call_deleteById_one_time() {
        // When
        checkoutService.deleteItemById(ITEM_ID);

        // Then
        verify(itemRepository, times(1)).deleteById(ITEM_ID);
    }

    @Test
    public void test_saveVasItem_should_call_save_one_time() {
        // When
        checkoutService.save(vasItem);

        // Then
        verify(vasItemRepository, times(1)).save(vasItem);
    }

    @Test
    public void test_findVasItemById_should_return_vasItem_when_vasItem_exists() {
        // Given
        when(vasItemRepository.findById(anyInt())).thenReturn(Optional.of(vasItem));

        // When
        VasItem foundVasItem = checkoutService.findVasItemById(anyInt());

        // Then
        assertEquals(vasItem, foundVasItem);
    }

    @Test
    public void test_findVasItemById_should_throw_CheckoutEntityNotFoundException_when_vasItem_does_not_exist() {
        // Given
        when(vasItemRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When and Then
        assertThrows(CheckoutEntityNotFoundException.class, () ->
                checkoutService.findVasItemById(anyInt()));
    }

    @Test
    public void test_findVasItemById_should_throw_CheckoutEntityNotFoundException_with_expected_message_when_vasItem_does_not_exist() {
        // Given
        when(vasItemRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When
        CheckoutEntityNotFoundException checkoutEntityNotFoundException =
                assertThrows(CheckoutEntityNotFoundException.class, () ->
                        checkoutService.findVasItemById(anyInt()));

        //Then
        assertEquals(checkoutEntityNotFoundException.getMessage(),
                "Vas Item not found with id: " + vasItem.getId());
    }

    @Test
    public void test_deleteVasItemById_should_call_deleteById_one_time() {
        // When
        checkoutService.deleteVasItemById(VAS_ITEM_ID);

        // Then
        verify(vasItemRepository, times(1)).deleteById(VAS_ITEM_ID);
    }

    @Test
    public void test_isVasItemExist_should_call_findById_one_time() {
        // Given
        when(vasItemRepository.findById(anyInt())).thenReturn(Optional.of(vasItem));

        // When
        checkoutService.isVasItemExist(anyInt());

        // Then
        verify(vasItemRepository, times(1)).findById(anyInt());
    }

    @Test
    public void test_isVasItemExist_when_vasItem_exists_should_return_true() {
        // Given
        when(vasItemRepository.findById(anyInt())).thenReturn(Optional.of(vasItem));

        // When
        boolean result = checkoutService.isVasItemExist(anyInt());

        // Then
        assertTrue(result);
    }

    @Test
    public void test_isVasItemExist_when_vasItem_does_not_exist_should_return_false() {
        // Given
        when(vasItemRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When
        boolean result = checkoutService.isVasItemExist(anyInt());

        // Then
        assertFalse(result);
    }

    @Test
    public void test_isDigitalItemExist_should_call_finByCategoryId_one_time() {
        // Given
        when(itemRepository.findByCategoryId(anyInt())).thenReturn(digitalItems);

        // When
        checkoutService.isDigitalItemExist(anyInt());

        // Then
        verify(itemRepository, times(1)).findByCategoryId(anyInt());
    }

    @Test
    public void test_isDigitalItemExist_when_digital_item_exists_should_return_true() {
        // Given
        when(itemRepository.findByCategoryId(anyInt())).thenReturn(digitalItems);

        // When
        boolean result = checkoutService.isDigitalItemExist(anyInt());

        // Then
        assertTrue(result);
    }

    @Test
    public void test_isDigitalItemExist_when_digital_item_does_not_exist_should_return_false() {
        // Given
        when(itemRepository.findByCategoryId(anyInt())).thenReturn(Collections.emptyList());

        // When
        boolean result = checkoutService.isDigitalItemExist(anyInt());

        // Then
        assertFalse(result);
    }

    @Test
    public void test_isOtherThanDigitalItemExist_should_call_isExistsByCategoryIdOtherThan_one_time() {
        // Given
        when(itemRepository.isExistsByCategoryIdOtherThan(anyInt())).thenReturn(true);

        // When
        checkoutService.isOtherThanDigitalItemExist(anyInt());

        // Then
        verify(itemRepository, times(1)).isExistsByCategoryIdOtherThan(anyInt());
    }

    @Test
    public void test_isOtherThanDigitalItemExist_when_other_item_exists_should_return_true() {
        // Given
        when(itemRepository.isExistsByCategoryIdOtherThan(anyInt())).thenReturn(true);

        // When
        boolean result = checkoutService.isOtherThanDigitalItemExist(anyInt());

        // Then
        assertTrue(result);
    }

    @Test
    public void test_isOtherThanDigitalItemExist_when_other_item_does_not_exists_should_return_false() {
        // Given
        when(itemRepository.isExistsByCategoryIdOtherThan(anyInt())).thenReturn(false);

        // When
        boolean result = checkoutService.isOtherThanDigitalItemExist(anyInt());

        // Then
        assertFalse(result);
    }
}