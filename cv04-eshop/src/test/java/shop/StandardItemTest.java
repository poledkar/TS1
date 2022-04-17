package shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * Unit tests for {@link StandardItem}.
 */
public class StandardItemTest {
    private static final int ID = 123;
    private static final String NAME = "Plush duck";
    private static final float PRICE = 7.89f;
    private static final String CATEGORY = "Plush toys";
    private static final int LOYALTY_POINTS = 3;

    private StandardItem item;

    @BeforeEach // StandardItem is a mutable class, so use new instance for each test
    public void prepareItem() {
        item = new StandardItem(ID, NAME, PRICE, CATEGORY, LOYALTY_POINTS);
    }

    // constructor

    @Test
    public void constructor_withId123_setsId123() {
        Assertions.assertEquals(ID, item.getID());
    }

    @Test
    public void constructor_withNamePlushDuck_setsNamePlushDuck() {
        Assertions.assertEquals(NAME, item.getName());
    }

    @Test
    public void constructor_withPrice7point89_setsPrice7point89() {
        Assertions.assertEquals(PRICE, item.getPrice());
    }

    @Test
    public void constructor_withCategoryPlushToys_setsCategoryPlushToys() {
        Assertions.assertEquals(CATEGORY, item.getCategory());
    }

    @Test
    public void constructor_withLoyaltyPoints3_setLoyaltyPoints3() {
        Assertions.assertEquals(LOYALTY_POINTS, item.getLoyaltyPoints());
    }

    // method copy()

    @Test
    public void copy_ofInstance_returnsDifferentInstance() {
        Assertions.assertNotSame(item, item.copy());
    }

    @Test
    public void copy_ofInstance_returnSomeInstance() {
        Assertions.assertNotNull(item.copy());
    }

    @Test
    public void copy_ofId123_returnsInstanceWithId123() {
        Assertions.assertEquals(ID, item.copy().getID());
    }

    @Test
    public void copy_ofNamePlushDuck_returnsInstanceWithNamePlushDuck() {
        Assertions.assertEquals(NAME, item.copy().getName());
    }

    @Test
    public void copy_ofPrice7point89_returnsInstanceWithPrice7point89() {
        Assertions.assertEquals(PRICE, item.copy().getPrice());
    }

    @Test
    public void copy_ofCategoryPlushToys_returnsInstanceWithCategoryPlushToys() {
        Assertions.assertEquals(CATEGORY, item.copy().getCategory());
    }

    // equals(Object other)

    @Test
    public void equals_withItself_returnsTrue() {
        Assertions.assertTrue(item.equals(item));
    }

    @Test
    public void equals_withItselfDiscounted_returnsFalse() {
        Date now = new Date();
        DiscountedItem other = new DiscountedItem(ID, NAME, PRICE, CATEGORY, 0, now, now);
        Assertions.assertFalse(item.equals(other));
    }


}
