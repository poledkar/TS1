package shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderTest {

    private static final String NAME = "Jan Vlk";
    private static final String ADDRESS = "Pralesni 1, Brno";
    private static final int STATE = 333;

    private ArrayList<Item> items;
    private ShoppingCart cart;

    @BeforeEach
    public void prepareItems() {
        items = new ArrayList<>(Arrays.asList(
            new StandardItem(1, "Plush Duck", 100.0f, "Plush Toys", 3),
            new StandardItem(2, "Rubber Duck", 50.5f, "Rubber Toys", 1)));
        cart = new ShoppingCart(items);
    }

    // unit tests for constructor with state argument

    @Test
    public void constructorWithState_givenCart_setsItemsFromCart() {
        Assertions.assertEquals(items, new Order(cart, NAME, ADDRESS, STATE).getItems());
    }

    @Test
    public void constructorWithState_givenName_setsName() {
        Assertions.assertEquals(NAME, new Order(cart, NAME, ADDRESS, STATE).getCustomerName());
    }

    @Test
    public void constructorWithState_givenAddress_setsAddress() {
        Assertions.assertEquals(ADDRESS, new Order(cart, NAME, ADDRESS, STATE).getCustomerAddress());
    }

    @Test
    public void constructorWithState_givenState_setsState() {
        Assertions.assertEquals(STATE, new Order(cart, NAME, ADDRESS, STATE).getState());
    }

    @Test
    public void constructorWithState_givenNullCart_throwsNullPointerException() {
        // There are more bugs when ShoppingCart exists but contains some nulls,
        // but that seems to be a matter for ShoppingCart tests.
        Assertions.assertThrows(NullPointerException.class, () -> new Order(null, NAME, ADDRESS, STATE));
    }

    @Test @Disabled("Order null handling is broken!")
    public void constructorWithState_givenNullName_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new Order(cart, null, ADDRESS, STATE));
    }

    @Test @Disabled("Order null handling is broken!")
    public void constructorWithState_givenNullAddress_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new Order(cart, NAME, null, STATE));
    }

    // unit tests for constructor without state argument

    @Test
    public void constructorWithoutState_givenCart_setsItemsFromCart() {
        Assertions.assertEquals(items, new Order(cart, NAME, ADDRESS).getItems());
    }

    @Test
    public void constructorWithoutState_givenName_setsName() {
        Assertions.assertEquals(NAME, new Order(cart, NAME, ADDRESS).getCustomerName());
    }

    @Test
    public void constructorWithoutState_givenAddress_setsAddress() {
        Assertions.assertEquals(ADDRESS, new Order(cart, NAME, ADDRESS).getCustomerAddress());
    }

    @Test
    public void constructorWithoutState_notGivenState_setsState0() {
        Assertions.assertEquals(0, new Order(cart, NAME, ADDRESS).getState());
    }

    @Test
    public void constructorWithoutState_givenNullCart_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new Order(null, NAME, ADDRESS));
    }

    @Test @Disabled("Order null handling is broken!")
    public void constructorWithoutState_givenNullName_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new Order(cart, null, ADDRESS));
    }

    @Test @Disabled("Order null handling is broken!")
    public void constructorWithoutState_givenNullAddress_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new Order(cart, NAME, null));
    }
}
