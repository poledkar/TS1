package shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Date;

/**
 * Unit tests for {@link StandardItem}.
 */
public class StandardItemTest {
    private static final int ID = 123;
    private static final String NAME = "Plush Duck";
    private static final float PRICE = 7.89f;
    private static final String CATEGORY = "Plush Toys";
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

    // Additional tests to disallow nulls

    @Test
    public void constructor_withNullName_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new StandardItem(ID, null, PRICE, CATEGORY, LOYALTY_POINTS));
    }

    @Test
    public void constructor_withNullCategory_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new StandardItem(ID, NAME, PRICE, null, LOYALTY_POINTS));
    }

    @Test
    public void setName_withNullName_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> item.setName(null));
    }

    @Test
    public void setCategory_withNullCategory_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> item.setCategory(null));
    }

    // method copy()

    @Test
    public void copy_ofInstance_returnsDifferentInstance() {
        Assertions.assertNotSame(item, item.copy());
    }

    @Test @Disabled("redundant, no further copy() test would pass otherwise")
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

    @ParameterizedTest(name = "item.equals(new StandardItem(" + ParameterizedTest.ARGUMENTS_PLACEHOLDER + "))")
    @MethodSource("equalsParameters")
    public void equals_withOther_returnsExpectedResult(
            int id, String name, float price, String category, int loyaltyPoints, boolean expected) {
        Item other = new StandardItem(id, name, price, category, loyaltyPoints);
        Assertions.assertEquals(expected, item.equals(other));
    }

    @ParameterizedTest(name = "new StandardItem(" + ParameterizedTest.ARGUMENTS_PLACEHOLDER + ").equals(item)")
    @MethodSource("equalsParameters")
    public void equals_ofOther_returnsExpectedResult(
            int id, String name, float price, String category, int loyaltyPoints, boolean expected) {
        Item other = new StandardItem(id, name, price, category, loyaltyPoints);
        Assertions.assertEquals(expected, other.equals(item));
    }

    public static Object[][] equalsParameters() {
        return new Object[][] {
                {ID, NAME, PRICE, CATEGORY, LOYALTY_POINTS, true},
                {ID+1, NAME, PRICE, CATEGORY, LOYALTY_POINTS, false},
                {ID, "Big "+NAME, PRICE, CATEGORY, LOYALTY_POINTS, false},
                {ID, NAME, PRICE*1.1f, CATEGORY, LOYALTY_POINTS, false},
                {ID, NAME, PRICE, "Small "+CATEGORY, LOYALTY_POINTS, false},
                {ID, NAME, PRICE, CATEGORY, LOYALTY_POINTS-1, false},
        //      {ID, null, PRICE, CATEGORY, LOYALTY_POINTS, false}, // no longer
        //      {ID, NAME, PRICE, null, LOYALTY_POINTS, false},     // allowed
                {ID, new String(NAME.toCharArray()), PRICE, CATEGORY, LOYALTY_POINTS, true},
                {ID, NAME, PRICE, new String(CATEGORY.toCharArray()), LOYALTY_POINTS, true}
        };
    }

    // additional tests not fitting parameterization scheme

    /* It's the point of these tests to check the correctness of equals() method,
     * hence IntelliJ Idea's warnings about equals()'s result being known constant
     * does not apply. assert(Not)Equals() methods are not guaranteed to actually
     * call equals(), hence can't be used in these tests. Corresponding
     * @SuppressWarnings do not seem to reliably work, hence inline noinspection
     * comments are used.
     */

    @Test
    public void equals_null_returnsFalse() {
        //noinspection ConstantConditions,SimplifiableAssertion
        Assertions.assertFalse(item.equals(null));
    }

    @Test
    public void equals_withItself_returnsTrue() {
        //noinspection SimplifiableAssertion,EqualsWithItself
        Assertions.assertTrue(item.equals(item));
    }

    @Test
    public void equals_withItselfDiscounted_returnsFalse() {
        Date now = new Date();
        DiscountedItem other = new DiscountedItem(ID, NAME, PRICE, CATEGORY, 0, now, now);
        //noinspection SimplifiableAssertion,EqualsBetweenInconvertibleTypes
        Assertions.assertFalse(item.equals(other));
    }
}
