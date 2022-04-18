package shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShoppingCartTest {

    // unit tests to ensure than ShoppingCart does not put nulls into Order

    @Test
    public void constructor_givenNullItems_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new ShoppingCart(null));
    }

    @Test
    public void constructor_withoutItems_setsSomeItemsList() {
        Assertions.assertNotNull(new ShoppingCart().getCartItems());
    }

    @Test
    public void addItem_givenNullItem_throwsNullPointerException() {
        ShoppingCart cart = new ShoppingCart();
        Assertions.assertThrows(NullPointerException.class, () -> cart.addItem(null));
        // This is not sufficient test, because addItem(null) puts null into list before throwing!
    }

    @Test
    public void addItem_givenNullItem_doesNotPutAnythingToItemList() {
        ShoppingCart cart = new ShoppingCart();
        try {
            cart.addItem(null);
        } catch (NullPointerException exception) {
            // that's expected and tested by test above
        }
        Assertions.assertTrue(cart.getCartItems().isEmpty());
    }

    // More tests would be needed to test rest of ShoppingCart functionality.
}
