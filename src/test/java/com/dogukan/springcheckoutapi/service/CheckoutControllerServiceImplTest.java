package com.dogukan.springcheckoutapi.service;

import com.dogukan.springcheckoutapi.builder.AddItemDtoBuilder;
import com.dogukan.springcheckoutapi.builder.AddVasItemDtoBuilder;
import com.dogukan.springcheckoutapi.builder.CartBuilder;
import com.dogukan.springcheckoutapi.builder.ItemBuilder;
import com.dogukan.springcheckoutapi.dto.AddItemDto;
import com.dogukan.springcheckoutapi.dto.AddVasItemDto;
import com.dogukan.springcheckoutapi.entity.Cart;
import com.dogukan.springcheckoutapi.entity.Item;
import com.dogukan.springcheckoutapi.entity.VasItem;
import com.dogukan.springcheckoutapi.error.BadRequestException;
import com.dogukan.springcheckoutapi.promotion.CategoryPromotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerServiceImplTest {
    @Mock
    private CheckoutService checkoutService;

    @Mock
    private PromotionService promotionService;

    @InjectMocks
    private CheckoutControllerServiceImpl checkoutControllerService;

    private final int MAXIMUM_ITEM_IN_CART = 30;
    private final int MAXIMUM_UNIQUE_ITEM_IN_CART = 10;
    private final int MAXIMUM_ITEM_QUANTITY = 10;
    private final int DIGITAL_ITEM_CATEGORY_ID = 7889;
    private final int MAXIMUM_DIGITAL_ITEM_QUANTITY = 5;
    private final int MAXIMUM_VAS_ITEM_IN_ITEM = 3;
    private final int FURNITURE_CATEGORY_ID = 1001;
    private final int ELECTRONICS_CATEGORY_ID = 3004;
    private final int VAS_ITEM_CATEGORY_ID = 3242;
    private final int VAS_ITEM_SELLER_ID = 5003;
    private final double MAXIMUM_TOTAL_PRICE_IN_CART = 500000.0;
    private Cart cart;
    private AddItemDto itemDto;
    private AddVasItemDto vasItemDto;
    private Item item;

    @BeforeEach
    void setup() {
        cart = new Cart();
        itemDto = new AddItemDto();
        vasItemDto = new AddVasItemDto();
        item = new Item();
    }

    @Test
    void test_CreateItemThenAddToCart_should_call_findCartById_one_time() {
        // Given
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        checkoutControllerService.createItemThenAddToCart(itemDto);

        // Then
        verify(checkoutService, times(1)).findCartById(anyInt());
    }

    @Test
    void test_CreateItemThenAddToCart_should_call_save_one_time() {
        // Given
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        checkoutControllerService.createItemThenAddToCart(itemDto);

        // Then
        verify(checkoutService, times(1)).save(cart);
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_when_item_already_exists() {
        // Given
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.isItemExist(anyInt())).thenReturn(true);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_when_item_already_exists_with_expected_message() {
        // Given
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.isItemExist(anyInt())).thenReturn(true);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The item already exists");
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_when_item_quantity_exceed_limit() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .quantity(MAXIMUM_ITEM_QUANTITY + 1)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_with_expected_message_when_item_quantity_exceed_limit() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .quantity(MAXIMUM_ITEM_QUANTITY + 1)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The total quantity of an item cannot exceed the number: "
                        + MAXIMUM_ITEM_IN_CART);
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_when_unique_items_exceed_limit() {
        // Given
        cart = new CartBuilder()
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_with_expected_message_when_unique_items_exceed_limit() {
        // Given
        cart = new CartBuilder()
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The total number of unique items in a cart cannot exceed the number: " +
                        MAXIMUM_UNIQUE_ITEM_IN_CART);
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_when_total_item_in_cart_exceed_limit() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .quantity(1)
                .build();
        cart = new CartBuilder()
                .addItem(new ItemBuilder()
                        .quantity(MAXIMUM_ITEM_IN_CART)
                        .build())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_with_expected_message_when_total_item_in_cart_exceed_limit() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .quantity(1)
                .build();
        cart = new CartBuilder()
                .addItem(new ItemBuilder()
                        .quantity(MAXIMUM_ITEM_IN_CART)
                        .build())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The total number of items in a cart cannot exceed the number: "
                        + MAXIMUM_ITEM_IN_CART);
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_when_try_to_add_physical_item_to_digital_cart() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        cart = new CartBuilder()
                .addItem(new ItemBuilder()
                        .categoryId(DIGITAL_ITEM_CATEGORY_ID)
                        .build())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.isDigitalItemExist(anyInt())).thenReturn(true);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_with_expected_message_when_try_to_add_physical_item_to_digital_cart() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        cart = new CartBuilder()
                .addItem(new ItemBuilder()
                        .categoryId(DIGITAL_ITEM_CATEGORY_ID)
                        .build())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.isDigitalItemExist(anyInt())).thenReturn(true);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "You cannot add a physical item to a digital cart");
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_when_digital_item_quantity_exceed_limit() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .categoryId(DIGITAL_ITEM_CATEGORY_ID)
                .quantity(6)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_with_expected_message_when_digital_item_quantity_exceed_limit() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .categoryId(DIGITAL_ITEM_CATEGORY_ID)
                .quantity(6)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The total quantity of a digital item cannot exceed the number: " +
                        MAXIMUM_DIGITAL_ITEM_QUANTITY);
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_when_try_to_add_digital_item_to_physical_cart() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .categoryId(DIGITAL_ITEM_CATEGORY_ID)
                .build();
        cart = new CartBuilder()
                .addItem(new ItemBuilder()
                        .categoryId(FURNITURE_CATEGORY_ID)
                        .build())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.isOtherThanDigitalItemExist(anyInt())).thenReturn(true);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_with_expected_message_when_try_to_add_digital_item_to_physical_cart() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .categoryId(DIGITAL_ITEM_CATEGORY_ID)
                .build();
        cart = new CartBuilder()
                .addItem(new ItemBuilder()
                        .categoryId(FURNITURE_CATEGORY_ID)
                        .build())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.isOtherThanDigitalItemExist(anyInt())).thenReturn(true);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "You cannot add a digital item to a physical cart");
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_when_price_exceed_total_price() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .price(MAXIMUM_TOTAL_PRICE_IN_CART + 1)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));
    }

    @Test
    void test_CreateItemThenAddToCart_should_throw_BadRequestException_with_expected_message_when_price_exceed_total_price() {
        // Given
        itemDto = new AddItemDtoBuilder()
                .price(MAXIMUM_TOTAL_PRICE_IN_CART + 1)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When and Then
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createItemThenAddToCart(itemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The total price of cart cannot exceed the value: "
                        + MAXIMUM_TOTAL_PRICE_IN_CART);
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_call_findCartById_one_time() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When
        checkoutControllerService.createVasItemThenAddToItem(vasItemDto);

        // Then
        verify(checkoutService, times(1)).findCartById(anyInt());
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_when_VasItem_already_exists() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);
        when(checkoutService.isVasItemExist(anyInt())).thenReturn(true);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_with_expected_message_when_VasItem_already_exists() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);
        when(checkoutService.isVasItemExist(anyInt())).thenReturn(true);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The vas item already exists");
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_when_categoryId_is_invalid() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(1000)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_with_expected_message_when_categoryId_is_invalid() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(1000)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The vas item must be created with the category ID of: "
                        + VAS_ITEM_CATEGORY_ID);
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_when_sellerId_is_invalid() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(4001)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_with_expected_message_when_sellerId_is_invalid() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(4001)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The vas item must be created with the seller ID of: "
                        + VAS_ITEM_SELLER_ID);
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_when_item_categoryId_is_invalid() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(1207)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_with_expected_message_when_item_categoryId_is_invalid() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(1207)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The vas item must be added to an item with the category ID of: "
                        + FURNITURE_CATEGORY_ID + " or " + ELECTRONICS_CATEGORY_ID);
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_when_total_item_in_cart_exceed_limit() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .quantity(MAXIMUM_ITEM_IN_CART + 1)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_with_expected_message_when_total_item_in_cart_exceed_limit() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .quantity(MAXIMUM_ITEM_IN_CART + 1)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The total number of items in a cart cannot exceed the number: "
                        + MAXIMUM_ITEM_IN_CART);
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_when_number_of_unique_vasItems_in_Item_exceed_limit() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .addVasItem(new VasItem())
                .addVasItem(new VasItem())
                .addVasItem(new VasItem())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_with_expected_message_when_number_of_unique_vasItems_in_Item_exceed_limit() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .addVasItem(new VasItem())
                .addVasItem(new VasItem())
                .addVasItem(new VasItem())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The total number of unique vas items added to an item cannot exceed the number: "
                        + MAXIMUM_VAS_ITEM_IN_ITEM);
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_when_vasItem_price_exceed_item_price() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .price(500)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .price(100)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_with_expected_message_when_vasItem_price_exceed_item_price() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .price(500)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .price(100)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The price of a vas item cannot exceed the price of an item");
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_when_vasItem_price_exceed_total_price_in_cart() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .price(1)
                .quantity(1)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .price(MAXIMUM_TOTAL_PRICE_IN_CART)
                .quantity(1)
                .build();
        Cart cart = new CartBuilder()
                .addItem(item)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_throw_BadRequestException_with_expected_message_when_vasItem_price_exceed_total_price_in_cart() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .price(1)
                .quantity(1)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .price(MAXIMUM_TOTAL_PRICE_IN_CART)
                .quantity(1)
                .build();
        cart = new CartBuilder()
                .addItem(item)
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(checkoutService.findItemById(anyInt())).thenReturn(item);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.createVasItemThenAddToItem(vasItemDto));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The total price of cart cannot exceed the value: "
                        + MAXIMUM_TOTAL_PRICE_IN_CART);
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_call_findItemById_one_time() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findItemById(anyInt())).thenReturn(item);
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        checkoutControllerService.createVasItemThenAddToItem(vasItemDto);

        // Then
        verify(checkoutService, times(1)).findItemById(anyInt());
    }

    @Test
    void test_CreateVasItemThenAddToItem_should_call_save_one_time() {
        // Given
        vasItemDto = new AddVasItemDtoBuilder()
                .categoryId(VAS_ITEM_CATEGORY_ID)
                .sellerId(VAS_ITEM_SELLER_ID)
                .build();
        item = new ItemBuilder()
                .categoryId(FURNITURE_CATEGORY_ID)
                .build();
        when(checkoutService.findItemById(anyInt())).thenReturn(item);
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        checkoutControllerService.createVasItemThenAddToItem(vasItemDto);

        // Then
        verify(checkoutService, times(1)).save(item);
    }

    @Test
    void test_ApplyPromotionToCart_should_call_findCartById_one_time() {
        // Given
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(promotionService.getPromotion()).thenReturn(new CategoryPromotion());

        // When
        checkoutControllerService.applyPromotionToCart();

        // Then
        verify(checkoutService, times(1)).findCartById(anyInt());
    }

    @Test
    void test_ApplyPromotionToCart_should_call_applyPromotion_three_times() {
        // Given
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);
        when(promotionService.getPromotion()).thenReturn(new CategoryPromotion());

        // When
        checkoutControllerService.applyPromotionToCart();

        // Then
        verify(promotionService, times(3)).applyPromotion(cart, 0.0);
    }

    @Test
    void test_DeleteItemById_should_call_isItemExist_one_time() {
        // Given
        when(checkoutService.isItemExist(anyInt())).thenReturn(true);

        // When
        checkoutControllerService.deleteItemById(anyInt());

        // Then
        verify(checkoutService, times(1)).isItemExist(anyInt());
    }

    @Test
    void test_DeleteItemById_should_Throw_BadRequestException_when_item_does_not_exists() {
        // Given
        when(checkoutService.isItemExist(anyInt())).thenReturn(false);

        // When and Then
        assertThrows(BadRequestException.class, () ->
                checkoutControllerService.deleteItemById(anyInt()));
    }

    @Test
    void test_DeleteItemById_should_Throw_BadRequestException_with_expected_message_when_item_does_not_exists() {
        // Given
        when(checkoutService.isItemExist(anyInt())).thenReturn(false);

        // When
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                checkoutControllerService.deleteItemById(anyInt()));

        // Then
        assertEquals(badRequestException.getMessage(),
                "The item does not exists");
    }

    @Test
    void test_DeleteItemById_should_call_deleteItemById_one_time() {
        // Given
        when(checkoutService.isItemExist(anyInt())).thenReturn(true);

        // When
        checkoutControllerService.deleteItemById(anyInt());

        // Then
        verify(checkoutService, times(1)).deleteItemById(anyInt());
    }

    @Test
    void test_DeleteAllItemsFromCart_should_call_findCartById_one_time() {
        // Given
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        checkoutControllerService.deleteAllItemsFromCart();

        // Then
        verify(checkoutService, times(1)).findCartById(anyInt());
    }

    @Test
    void test_DeleteAllItemsFromCart_should_call_deleteItem_three_times_to_reset_cart() {
        // Given
        cart = new CartBuilder()
                .addItem(new Item())
                .addItem(new Item())
                .addItem(new Item())
                .build();
        when(checkoutService.findCartById(anyInt())).thenReturn(cart);

        // When
        checkoutControllerService.deleteAllItemsFromCart();

        // Then
        verify(checkoutService, times(3)).deleteItem(any(Item.class));
    }

}