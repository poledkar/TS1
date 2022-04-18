package storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import shop.Item;
import shop.StandardItem;

public class ItemStockTest {

    private static Item item;

    @BeforeAll
    public static void prepareItem() { // item is not modified by tests
        item = new StandardItem(123, "Rubber Duck", 120.5f, "Rubber Toys", 7);
    }

    // constructor unit tests

    @Test
    public void constructor_givenRefItem_setsRefItem() {
        Assertions.assertEquals(item, new ItemStock(item).getItem());
    }

    @Test
    public void constructor_givenRefItem_setsZeroCount() {
        Assertions.assertEquals(0, new ItemStock(item).getCount());
    }

    @Test
    public void constructor_givenNullRefItem_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new ItemStock(null));
    }

    // parameterized test for increaseItemCount(int)

    @ParameterizedTest(name = "increaseItemCount_firstBy{0}_setsCountTo{0}")
    @ValueSource(ints = {0, 1, 2, 10, 200, -333, Integer.MAX_VALUE, Integer.MIN_VALUE})
    public void increaseItemCount_oneInvocation_setsCount(int argument) {
        ItemStock stock = new ItemStock(item);
        stock.increaseItemCount(argument);
        Assertions.assertEquals(argument, stock.getCount());
    }

    @ParameterizedTest(name = "increaseItemCount_from{0}Adds{1}_increasesTo{2}")
    @CsvSource({"0,1,1", "1,2,3", "70,30,100", "10,-3,7", "10,-13,-3"})
    public void increaseItemCount_twoInvocations_accumulate(int first, int second, long count) {
        ItemStock stock = new ItemStock(item);
        stock.increaseItemCount(first);
        stock.increaseItemCount(second);
        Assertions.assertEquals(count, stock.getCount());
    }

    @ParameterizedTest(name = "increaseItemCount_overflowBy{0}And{1}_throwsIllegalStateException")
    @CsvSource({"2000000000,1000000000", "1000000000,2000000000", "-2000000000,-1000000000"})
    public void increaseItemCount_overflow_throwsIllegalStateException(int first, int second) {
        ItemStock stock = new ItemStock(item);
        stock.increaseItemCount(first);
        Assertions.assertThrows(IllegalStateException.class, () -> stock.increaseItemCount(second));
    }

    // parameterized test for decreaseItemCount(int)

    @ParameterizedTest(name = "decreaseItemCount_firstBy{0}_setsCountToMinus{0}")
    @ValueSource(ints = {0, 1, 2, 10, 200, -333, Integer.MAX_VALUE})
    public void decreaseItemCount_oneInvocation_setsNegatedCount(int argument) {
        ItemStock stock = new ItemStock(item);
        stock.decreaseItemCount(argument);
        Assertions.assertEquals(- (long) argument, stock.getCount());
    }

    @Test
    public void decreaseItemCount_minInteger_throwsIllegalStateException() {
        Assertions.assertThrows(IllegalStateException.class, () -> new ItemStock(item).decreaseItemCount(Integer.MIN_VALUE));
    }

    @ParameterizedTest(name = "decreaseItemCount_from{0}Removes{1}_result{2}")
    @CsvSource({"0,1,-1", "10,2,8", "5,7,-2", "1234,12,1222"})
    public void decreaseItemCount_twoInvocations_accumulate(int original, int amount, long count) {
        ItemStock stock = new ItemStock(item);
        stock.decreaseItemCount(- original);
        stock.decreaseItemCount(amount);
        Assertions.assertEquals(count, stock.getCount());
    }

    @ParameterizedTest(name = "decreaseItemCount_underflowBy{0}And{1}_throwsIllegalStateException")
    @CsvSource({"2000000000,1000000000", "1000000000,2000000000", "-2000000000,-1000000000"})
    public void decreaseItemCount_underflow_throwsIllegalStateException(int first, int second) {
        ItemStock stock = new ItemStock(item);
        stock.decreaseItemCount(first);
        Assertions.assertThrows(IllegalStateException.class, () -> stock.decreaseItemCount(second));
    }
}
