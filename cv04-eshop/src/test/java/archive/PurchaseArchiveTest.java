package archive;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import shop.Item;
import shop.Order;
import shop.ShoppingCart;
import shop.StandardItem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PurchaseArchiveTest {

    private static final String HEADER = "ITEM PURCHASE STATISTICS:";
    private static final String NAME = "Jan Vlk";
    private static final String ADDRESS = "Pralesni 1, Brno";

    private static PrintStream originalStdOut;
    private static ByteArrayOutputStream outContent;

    private static final Item item1 = new StandardItem(123, "Ping", 3.33f, "Toys", 1);
    private static final Item item2 = new StandardItem(456, "Pang", 6.66f, "Toys", 2);
    private static final Item item3 = new StandardItem(789, "Pong", 9.99f, "Toys", 3);
    private static final Item item4 = new StandardItem(987, "Ball", 11.5f, "Sport", 4);

    private static final int ITEM1_COUNT = 11;
    private static final int ITEM2_COUNT = 22;
    private static final int ITEM3_COUNT = 33;

    private static final String ITEM1_ENTRY = "mock1";
    private static final String ITEM2_ENTRY = "mock2";
    private static final String ITEM3_ENTRY = "mock3";

    @BeforeAll
    public static void storeOriginalStdOut() {
        originalStdOut = System.out;
        outContent = new ByteArrayOutputStream();
    }

    @BeforeEach
    public void captureStdOut() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStdOut() {
        System.setOut(originalStdOut);
        outContent.reset();
    }

    // Generic classes can't be properly mocked just by Mockito.mock(class)!
    // private ArrayList<Order> mockOrderArchive = Mockito.mock(ArrayList.class);
    private static class ArrayListOfOrders extends ArrayList<Order> {}
    private ArrayList<Order> mockOrderArchive;
    private ItemPurchaseArchiveEntry mockEntry2;
    private HashMap<Integer, ItemPurchaseArchiveEntry> mockItemPurchaseArchive;
    private PurchasesArchive archive;

    @BeforeEach
    public void mockArchive() {
        ItemPurchaseArchiveEntry mockEntry1 = Mockito.mock(ItemPurchaseArchiveEntry.class);
        Mockito.when(mockEntry1.getCountHowManyTimesHasBeenSold()).thenReturn(ITEM1_COUNT);
        Mockito.when(mockEntry1.toString()).thenReturn(ITEM1_ENTRY);

        mockEntry2 = Mockito.mock(ItemPurchaseArchiveEntry.class);
        Mockito.when(mockEntry2.getCountHowManyTimesHasBeenSold()).thenReturn(ITEM2_COUNT);
        Mockito.when(mockEntry2.toString()).thenReturn(ITEM2_ENTRY);

        ItemPurchaseArchiveEntry mockEntry3 = Mockito.mock(ItemPurchaseArchiveEntry.class);
        Mockito.when(mockEntry3.getCountHowManyTimesHasBeenSold()).thenReturn(ITEM3_COUNT);
        Mockito.when(mockEntry3.toString()).thenReturn(ITEM3_ENTRY);

        mockOrderArchive = Mockito.mock(ArrayListOfOrders.class);

        mockItemPurchaseArchive = new LinkedHashMap<>(); // keep predictable order!
        mockItemPurchaseArchive.put(item1.getID(), mockEntry1);
        mockItemPurchaseArchive.put(item2.getID(), mockEntry2);
        mockItemPurchaseArchive.put(item3.getID(), mockEntry3);

        archive = new PurchasesArchive(mockItemPurchaseArchive, mockOrderArchive);
    }

    // printItemPurchaseStatistics() tests

    @Test
    public void printItemPurchaseStatistics_emptyArchive_printsHeaderOnly() {
        new PurchasesArchive().printItemPurchaseStatistics();
        Assertions.assertEquals(HEADER + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void printItemPurchaseStatistics_threeRecords_printsAppropriateReport() {
        archive.printItemPurchaseStatistics();
        String[] expected = {
                HEADER,
                ITEM1_ENTRY,
                ITEM2_ENTRY,
                ITEM3_ENTRY
        };
        Assertions.assertArrayEquals(expected, outContent.toString().lines().toArray(String[]::new));
    }

    // getHowManyTimesHasBeenItemSold() tests

    @Test
    public void getHowManyTimesHasBeenItemSold_newerSold_returns0() {
        Assertions.assertEquals(0, archive.getHowManyTimesHasBeenItemSold(item4));
    }

    @ParameterizedTest(name = "getHowManyTimesHasBeenItemSold_itemSold{1}Times_returns{1}")
    @MethodSource("salesParameters")
    public void getHowManyTimesHasBeenItemSold_itemSold_returnsCount(Item item, int count) {
        Assertions.assertEquals(count, archive.getHowManyTimesHasBeenItemSold(item));
    }

    public static Object[][] salesParameters() {
        return new Object[][] {
                { item1, ITEM1_COUNT },
                { item2, ITEM2_COUNT },
                { item3, ITEM3_COUNT }
        };
    }

    // putOrderToPurchasesArchive tests

    @Test
    public void putOrderToPurchasesArchive_emptyOrder_addsOrderToArchive() {
        Order order = new Order(new ShoppingCart(), NAME, ADDRESS);
        archive.putOrderToPurchasesArchive(order);
        Mockito.verify(mockOrderArchive).add(order);
    }

    @Test
    public void putOrderToPurchasesArchive_orderedAlreadySoldItem_increasesCount() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(item2);
        Order order = new Order(cart, NAME, ADDRESS);
        archive.putOrderToPurchasesArchive(order);
        Mockito.verify(mockEntry2).increaseCountHowManyTimesHasBeenSold(1);
    }

    @Test
    public void putOrderToPurchasesArchive_orderedNewItem_createsNewItemPurchaseArchiveEntry() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(item4);
        Order order = new Order(cart, NAME, ADDRESS);
        try (MockedConstruction<ItemPurchaseArchiveEntry> mock = Mockito.mockConstruction(ItemPurchaseArchiveEntry.class)) {
            archive.putOrderToPurchasesArchive(order);
            Assertions.assertEquals(1, mock.constructed().size());
            // But there does not seem to be access to constructor parameters...
        }
    }

    @Test
    public void putOrderToPurchasesArchive_orderedNewItem_setsProperItemInNewItemPurchaseArchiveEntry() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(item4);
        Order order = new Order(cart, NAME, ADDRESS);
        archive.putOrderToPurchasesArchive(order);
        Assertions.assertEquals(item4, mockItemPurchaseArchive.get(item4.getID()).getRefItem());
    }

    @Test
    public void putOrderToPurchasesArchive_orderedNewItemTwice_thatItemSoldTwoTimes() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(item4);
        cart.addItem(item4);
        Order order = new Order(cart, NAME, ADDRESS);
        archive.putOrderToPurchasesArchive(order);
        Assertions.assertEquals(2, mockItemPurchaseArchive.get(item4.getID()).getCountHowManyTimesHasBeenSold());
    }
}
